package com.bizuinfo.usuario.dao;

import com.bizuinfo.acesso.util.JPAutil;
import com.bizuinfo.usuario.model.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioDAO {
    public Optional<Usuario> buscarPorEmail(String email) {
        try (EntityManager em = JPAutil.getEntityManager()) {
            Usuario u = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                          .setParameter("email", email)
                          .getSingleResult();
            return Optional.of(u);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Usuario> buscarPorToken(String token) {
        try (EntityManager em = JPAutil.getEntityManager()) {
            Usuario u = em.createQuery(
                    """
                    SELECT u
                    FROM Usuario u
                    WHERE u.tokenVerificacao = :token
                       OR u.tokenRecuperacao = :token
                    """,
                    Usuario.class
                )
                .setParameter("token", token)
                .getSingleResult();
            return Optional.of(u);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void salvar(Usuario u) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Usuario> listarTodos() {
        try (EntityManager em = JPAutil.getEntityManager()) {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        }
    }

    public Optional<Usuario> buscarPorId(Long id) {
        try (EntityManager em = JPAutil.getEntityManager()) {
            Usuario u = em.find(Usuario.class, id);
            return Optional.ofNullable(u);
        }
    }

    public void remover(Long id) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, id);
            if (u != null) {
                em.remove(u);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
