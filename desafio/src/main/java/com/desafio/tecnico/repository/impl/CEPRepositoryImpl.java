package com.desafio.tecnico.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desafio.tecnico.config.HibernateConfig;
import com.desafio.tecnico.entity.CEP;
import com.desafio.tecnico.repository.CEPRepository;

public class CEPRepositoryImpl implements CEPRepository {

    private final SessionFactory sessionFactory;

    public CEPRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public void save(CEP cep) {

        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(cep);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar CEP", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public CEP findByCEPNumber(String cepNumber) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM CEP WHERE cep = :cepNumber", CEP.class)
                    .setParameter("cepNumber", cepNumber)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar CEP: " + cepNumber, e);
        }
    }

}
