package com.bizuinfo.infra.dao;

import com.bizuinfo.infra.util.JPAutil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public abstract class GenericoDAO<T> {

    private final Class<T> classeEntidade;

    protected GenericoDAO(Class<T> classeEntidade) {
        this.classeEntidade = classeEntidade;
    }

    public Optional<T> buscarPorId(Long id) {

        try (EntityManager em = JPAutil.getEntityManager()) {
            return Optional.ofNullable(
                    em.find(classeEntidade, id)
            );
        }
    }

    public void salvar(T entidade) {

        EntityManager em = JPAutil.getEntityManager();

        try {

            em.getTransaction().begin();
            em.merge(entidade);
            em.flush();
            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public void remover(Long id) {

        EntityManager em = JPAutil.getEntityManager();

        try {

            em.getTransaction().begin();

            T referencia = em.getReference(
                    classeEntidade,
                    id
            );

            em.remove(referencia);
            em.flush();
            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException(
                    "Erro ao remover registro: " + e.getMessage(),
                    e
            );

        } finally {
            em.close();
        }
    }

    public List<T> listarTodos() {

        try (EntityManager em = JPAutil.getEntityManager()) {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(classeEntidade);
            Root<T> root = cq.from(classeEntidade);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        }
    }
}