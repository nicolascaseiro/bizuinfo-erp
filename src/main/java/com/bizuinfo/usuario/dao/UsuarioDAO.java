package com.bizuinfo.usuario.dao;

import com.bizuinfo.infra.util.JPAutil;
import com.bizuinfo.usuario.model.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

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
}
