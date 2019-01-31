package no.eritec.demo.jpahibernate.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import no.eritec.demo.jpahibernate.domain.Employee;

public interface EmployeeBatchRepository extends CrudRepository<Employee, Long> {
	public int save(List<Employee> emps);
}