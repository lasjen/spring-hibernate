package no.eritec.demo.jpahibernate.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import no.eritec.demo.jpahibernate.domain.Employee;

public class EmployeeBatchRepositoryImpl implements EmployeeBatchRepository {
	@PersistenceContext
    private EntityManager entityManager;
	
	@Transactional(readOnly=true)
	public int save(List<Employee> emps)  {
		//int entityCount = 50;
		//int batchSize = 25;

		EntityTransaction entityTransaction = entityManager
		    .getTransaction();
		 
		try {
		    entityTransaction.begin();
		    Iterator<Employee> iterator = emps.iterator();
		    while (iterator.hasNext()) {
		        entityManager.persist(iterator.next());
		    }
		 
		    entityTransaction.commit();
		} catch (RuntimeException e) {
		    if (entityTransaction.isActive()) {
		        entityTransaction.rollback();
		        return 0;
		    }
		    throw e;
		} finally {
		    entityManager.close();
		}
		
		return emps.size();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Employee arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Employee> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Employee> findAllById(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Employee> findById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Employee> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Employee> Iterable<S> saveAll(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
