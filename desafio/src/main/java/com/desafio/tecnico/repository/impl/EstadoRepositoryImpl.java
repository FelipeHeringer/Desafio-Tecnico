package com.desafio.tecnico.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.desafio.tecnico.config.HibernateConfig;
import com.desafio.tecnico.entity.Estado;
import com.desafio.tecnico.repository.EstadoRepository;

public class EstadoRepositoryImpl implements EstadoRepository {

    private final SessionFactory sessionFactory;

    public EstadoRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public Estado findByUF(String uf) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Estado WHERE uf = :uf", Estado.class)
                    .setParameter("uf", uf.toUpperCase())
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estado por UF: " + uf , e);
        }
    }

}
