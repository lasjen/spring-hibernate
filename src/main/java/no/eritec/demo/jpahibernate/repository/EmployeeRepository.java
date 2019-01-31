package no.eritec.demo.jpahibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.eritec.demo.jpahibernate.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{	
	Employee findEmployeeByEmpno(int empno); 
}
