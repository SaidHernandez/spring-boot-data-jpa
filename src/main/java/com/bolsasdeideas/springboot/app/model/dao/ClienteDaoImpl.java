package com.bolsasdeideas.springboot.app.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsasdeideas.springboot.app.model.entity.Cliente;


@Repository("ClienteDaoJPA") // se coloca un qualifier
public class ClienteDaoImpl implements IClienteDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		if(cliente.getId() > 0) {
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}	
	}

	@Override
	public Cliente findOne(long id) {
		return em.find(Cliente.class, id);
	}
}
