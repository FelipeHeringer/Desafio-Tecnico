package com.desafio.tecnico.repository.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.desafio.tecnico.config.HibernateConfig;
import com.desafio.tecnico.entity.Casa;
import com.desafio.tecnico.repository.CasaRepository;

@ApplicationScoped
public class CasaRepositoryImpl implements CasaRepository {

    private final SessionFactory sessionFactory;

    public CasaRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getInstance().getSessionFactory();
    }

    @Override
    public Casa save(Casa casa) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(casa);
            transaction.commit();

            return casa;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Erro ao salvar Casa", e);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @Override
    public Casa update(Casa casa) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Casa updated = session.merge(casa);

            session.flush();

            transaction.commit();

            return updated;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println("ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar Casa ID: " + casa.getId(), e);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @Override
    public void deactivate(Long idCasa) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.createMutationQuery(
                    "UPDATE Casa SET ativo = false WHERE id = :id")
                    .setParameter("id", idCasa)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Erro ao inativar Casa ID: " + idCasa, e);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @Override
    public List<Casa> findAllByOwner(Long propietarioId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT c FROM Casa c " +
                            "JOIN FETCH c.endereco e " +
                            "JOIN FETCH e.cidade " +
                            "JOIN FETCH e.estado " +
                            "JOIN FETCH e.cep " +
                            "WHERE c.propietario.id = :propietarioId " +
                            "AND c.ativo = true",
                    Casa.class)
                    .setParameter("propietarioId", propietarioId)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Casas do Proprietário: " + propietarioId, e);
        }
    }

    @Override
    public Casa findById(Long idCasa) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT c FROM Casa c " +
                            "JOIN FETCH c.endereco e " +
                            "JOIN FETCH e.cidade " +
                            "JOIN FETCH e.estado " +
                            "JOIN FETCH e.cep " +
                            "WHERE c.id = :idCasa",
                    Casa.class)
                    .setParameter("idCasa", idCasa)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Casa ID: " + idCasa, e);
        }
    }
}
