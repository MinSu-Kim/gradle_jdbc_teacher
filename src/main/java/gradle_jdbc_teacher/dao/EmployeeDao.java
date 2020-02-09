package gradle_jdbc_teacher.dao;

import java.util.List;

import gradle_jdbc_teacher.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByNo(Employee emp);
	List<Employee> selectEmployeeByAll();
	
	int insertEmployee(Employee emp);
	int updateEmployee(Employee emp);
	int deleteEmployee(Employee emp);
	
	Employee isEqualPassword(Employee emp);
}
