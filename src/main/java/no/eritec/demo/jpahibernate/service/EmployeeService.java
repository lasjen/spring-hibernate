package no.eritec.demo.jpahibernate.service;

import java.util.List;

import no.eritec.demo.jpahibernate.domain.Employee;

public interface EmployeeService {

	Employee save(Employee emp);

	Employee findEmployeeByEmpno(int empno);
	
	List<Employee> findAll();

	int save(List<Employee> emps);
	
	long countEmployees();
}
