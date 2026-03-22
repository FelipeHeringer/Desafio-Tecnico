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
    public Endereco save(Endereco endereco) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(endereco);
            transaction.commit();

            return endereco;
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

@Override
public Endereco findByAllFields(Long cepId, Long estadoId, Long cidadeId,
                                 String bairro, String logradouro,
                                 Integer numero, String complemento) {
    try (Session session = sessionFactory.openSession()) {
        String hql = "SELECT e FROM Endereco e " +
                     "JOIN FETCH e.cep " +
                     "JOIN FETCH e.estado " +
                     "JOIN FETCH e.cidade " +
                     "WHERE e.cep.id = :cepId " +
                     "AND e.estado.id = :estadoId " +
                     "AND e.cidade.id = :cidadeId " +
                     "AND e.bairro = :bairro " +
                     "AND e.logradouro = :logradouro " +
                     "AND e.numero = :numero " +
                     "AND (:complemento IS NULL AND e.complemento IS NULL " +
                     "     OR e.complemento = :complemento)";

        return session.createQuery(hql, Endereco.class)
                .setParameter("cepId", cepId)
                .setParameter("estadoId", estadoId)
                .setParameter("cidadeId", cidadeId)
                .setParameter("bairro", bairro)
                .setParameter("logradouro", logradouro)
                .setParameter("numero", numero)
                .setParameter("complemento", complemento)
                .setMaxResults(1)
                .uniqueResult();
    } catch (Exception e) {
        throw new RuntimeException("Erro ao buscar endereço por campos", e);
    }
}

}
