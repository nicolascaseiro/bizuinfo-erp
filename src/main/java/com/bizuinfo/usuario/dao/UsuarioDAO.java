package com.bizuinfo.usuario.dao;

import com.bizuinfo.infra.dao.GenericoDAO;
import com.bizuinfo.infra.util.JPAutil;
import com.bizuinfo.usuario.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioDAO extends GenericoDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Optional<Usuario> buscarPorEmail(String email) {

        try (EntityManager em = JPAutil.getEntityManager()) {

            Usuario u = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email",
                            Usuario.class
                    )
                    .setParameter("email", email)
                    .getSingleResult();

            return Optional.of(u);

        } catch (NoResultException e) {

            return Optional.empty();

        }
    }

    public List<Usuario> listarTodos() {

        try (EntityManager em = JPAutil.getEntityManager()) {

            return em.createQuery(
                    "SELECT u FROM Usuario u ORDER BY u.id",
                    Usuario.class
            ).getResultList();
        }
    }

    public Optional<Usuario> buscarPorToken(String token) {

        try (EntityManager em = JPAutil.getEntityManager()) {

            Usuario u = em.createQuery(
                            """
                                    SELECT u
                                    FROM Usuario u
                                    WHERE u.tokenVerificacao = :token
                                       OR u.tokenReset = :token
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
}