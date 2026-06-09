package com.bizuinfo.produto.dao;

import com.bizuinfo.infra.dao.GenericoDAO;
import com.bizuinfo.infra.util.JPAutil;
import com.bizuinfo.produto.model.Produto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;

@ApplicationScoped
public class ProdutoDAO extends GenericoDAO<Produto> {

    public ProdutoDAO() {
        super(Produto.class);
    }

    public Optional<Produto> buscarPorNome(String nome) {

        try (EntityManager em = JPAutil.getEntityManager()) {

            Produto produto = em.createQuery(
                            "SELECT p FROM Produto p WHERE p.nome = :nome",
                            Produto.class
                    )
                    .setParameter("nome", nome)
                    .getSingleResult();

            return Optional.of(produto);

        } catch (NoResultException e) {

            return Optional.empty();
        }
    }

    public void removerCategoriaDosProdutos(Long categoriaId) {

        EntityManager em = JPAutil.getEntityManager();

        try {

            em.getTransaction().begin();

            em.createQuery("""
                            UPDATE Produto p
                            SET p.categoria = NULL
                            WHERE p.categoria.id = :categoriaId
                            """)
                    .setParameter("categoriaId", categoriaId)
                    .executeUpdate();

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
}
