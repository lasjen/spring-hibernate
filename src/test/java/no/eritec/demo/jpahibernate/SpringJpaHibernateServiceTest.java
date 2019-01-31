package no.eritec.demo.jpahibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import no.eritec.demo.jpahibernate.domain.Employee;
//import no.eritec.demo.jpahibernate.repository.EmployeeRepository;
import no.eritec.demo.jpahibernate.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJpaHibernateServiceTest {
	@Autowired
	private EmployeeService service;

	@Test
	public void countEmployees() {
		long count = service.countEmployees();
		
		assertEquals(count,(long) 4);
	}
	@Test
	public void findAllUsers() {
		List<Employee> employees = service.findAll();
		assertNotNull(employees);
		assertTrue(!employees.isEmpty());
	}

	@Test
	public void findUserById() {
		Employee emp = service.findEmployeeByEmpno(1000);
		assertNotNull(emp);
	}

	@Test
	public void createEmployee() {
		Employee emp = new Employee();
		emp.setEname("JOHNNY");
		emp.setJob("Sales");
		emp.setHiredate(new Date(new java.util.Date().getTime()));
		emp.setManagerNo(1000);
		emp.setSalary(5000);
		emp.setDeptno(30);
		Employee savedEmp = service.save(emp);

		Employee newEmp = service.findEmployeeByEmpno(savedEmp.getEmpno());

		assertNotNull(newEmp);
		assertEquals("JOHNNY", newEmp.getEname());
		assertEquals(5000, newEmp.getSalary());
	}

	@Test
	public void saveList() {
		List<Employee> emps = getListOfEmployees();

		long result = service.save(emps);

		//long cnt = service.countEmployees();

		assertEquals(5, result);
	}

	public List<Employee> getListOfEmployees() {
		List<Employee> emps = new ArrayList<Employee>();

		Employee emp1 = new Employee();
		Employee emp2 = new Employee();
		Employee emp3 = new Employee();
		Employee emp4 = new Employee();
		Employee emp5 = new Employee();
		
		emp1.setEname("JOHNNY");
		emp1.setJob("Sales");
		emp1.setHiredate(new Date(new java.util.Date().getTime()));
		emp1.setManagerNo(1000);
		emp1.setSalary(5000);
		emp1.setDeptno(30);
		
		emp2.setEname("HARRY");
		emp2.setJob("Sales");
		emp2.setHiredate(new Date(new java.util.Date().getTime()));
		emp2.setManagerNo(1000);
		emp2.setSalary(2000);
		emp2.setDeptno(30);
		
		emp3.setEname("SUSIE");
		emp3.setJob("Sales");
		emp3.setHiredate(new Date(new java.util.Date().getTime()));
		emp3.setManagerNo(1000);
		emp3.setSalary(7000);
		emp3.setDeptno(30);
		
		emp4.setEname("LARRY");
		emp4.setJob("Sales");
		emp4.setHiredate(new Date(new java.util.Date().getTime()));
		emp4.setManagerNo(1000);
		emp4.setSalary(1000);
		emp4.setDeptno(30);
		
		emp5.setEname("DENISE");
		emp5.setJob("Sales");
		emp5.setHiredate(new Date(new java.util.Date().getTime()));
		emp5.setManagerNo(1000);
		emp5.setSalary(1000);
		emp5.setDeptno(30);

		emps.add(emp1);
		emps.add(emp2);
		emps.add(emp3);
		emps.add(emp4);
		emps.add(emp5);
		
		return emps;
	}
}
