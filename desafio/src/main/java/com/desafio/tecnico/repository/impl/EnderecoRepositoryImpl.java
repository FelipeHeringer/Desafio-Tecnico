package com.desafio.tecnico.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desafio.tecnico.config.HibernateConfig;
import com.desafio.tecnico.entity.Endereco;
import com.desafio.tecnico.repository.EnderecoRepository;

public class EnderecoRepositoryImpl implements EnderecoRepository {

    private final SessionFactory sessionFactory;

    public EnderecoRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public void save(Endereco endereco) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(endereco);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar Endereço", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Endereco update(Endereco endereco) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Endereco updated = session.merge(endereco);
            session.flush();
            transaction.commit();

            return updated;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Erro ao atualizar Endereco ID: " + endereco.getId(), e);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

}
