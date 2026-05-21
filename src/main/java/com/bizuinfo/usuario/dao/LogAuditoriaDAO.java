package com.bizuinfo.usuario.dao;

import com.bizuinfo.acesso.util.JPAutil;
import com.bizuinfo.usuario.model.LogAuditoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class LogAuditoriaDAO {

    public void salvar(LogAuditoria log) {
        try (EntityManager em = JPAutil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}