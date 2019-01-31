package no.eritec.demo.jpahibernate.service;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
//import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;

//import javax.ws.rs.Path;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.ext.ContextResolver;
//import javax.ws.rs.ext.Providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.eritec.demo.jpahibernate.config.OracleMetricDataSource;
import no.eritec.demo.jpahibernate.context.OracleMetricContext;
import no.eritec.demo.jpahibernate.domain.Employee;
import no.eritec.demo.jpahibernate.repository.EmployeeBatchRepositoryImpl;
import no.eritec.demo.jpahibernate.repository.EmployeeRepository;
import no.eritec.demo.jpahibernate.repository.WaitRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired 
	private EmployeeBatchRepositoryImpl empBatch;
	
	@Autowired 
	private WaitRepository waitRepo;
	
	@Autowired
	private OracleMetricDataSource ds;

	//@Context
	//private Providers providers;

	@Override
	public Employee findEmployeeByEmpno(int empno) {
		ds.setContext("HibernateTest", "findUserById", "ECID");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return empRepo.findEmployeeByEmpno(empno);
	}

	@Override
	public Employee save(Employee emp) {
		ds.setContext("HibernateTest", "save","ECID");
		Employee savedEmp = empRepo.save(emp);
		Employee newEmp = empRepo.findEmployeeByEmpno(savedEmp.getEmpno());
		return newEmp;
	}
	
	@Override
	public int save(List<Employee> emps) {
		ds.setContext("HibernateTest", "saveBatch","ECID");
		List<Employee> l = empRepo.saveAll(emps);
		return l.size();
	}

	@Override
	public List<Employee> findAll() {
		ds.setContext("HibernateTest","findAllUsers", "ECID");
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Integer> i = waitRepo.getIdWithWait(1, 5);
		return empRepo.findAll();
	}
	
	@Override
	public long countEmployees() {
		ds.setContext("HibernateTest","countEmployees", "ECID");
		return empRepo.count();
	}
	
	private void setContext(String action, @Context Providers providers) {
		ContextResolver<OracleMetricContext> cr = providers.getContextResolver(OracleMetricContext.class,
				MediaType.WILDCARD_TYPE);
		OracleMetricContext<String> c = cr.getContext(String.class);
		String r = c.get(action);
		//return "response: " + r;
	}
}
