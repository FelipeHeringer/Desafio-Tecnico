package com.desafio.tecnico.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desafio.tecnico.config.HibernateConfig;
import com.desafio.tecnico.entity.Propietario;
import com.desafio.tecnico.repository.PropietarioRepository;

public class PropietarioRepositoryImpl implements PropietarioRepository {

    private SessionFactory sessionFactory;

    public PropietarioRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public void save(Propietario propietario) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(propietario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Erro ao salvar Propietário", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Propietario findByCpf(String cpf) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Propietario WHERE cpf = :cpf", Propietario.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Propietário do CPF: " + cpf, e);
        }
    }

}
