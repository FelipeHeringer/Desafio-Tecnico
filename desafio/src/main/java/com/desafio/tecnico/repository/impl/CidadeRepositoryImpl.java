package com.desafio.tecnico.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.desafio.tecnico.config.HibernateConfig;
import com.desafio.tecnico.entity.Cidade;
import com.desafio.tecnico.repository.CidadeRepository;

public class CidadeRepositoryImpl implements CidadeRepository{

    private final SessionFactory sessionFactory;

    public CidadeRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public Cidade findByCityName(String cityName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Cidade WHERE nomeCidade = :cityName", Cidade.class)
                    .setParameter("cityName", cityName)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cidade: " + cityName , e);
        }
    }
    
}
