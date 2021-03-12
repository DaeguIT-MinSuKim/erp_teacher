package erp_teacher.dao;

import java.util.List;

import erp_teacher.dto.Employee;
import erp_teacher.dto.Title;

public interface EmployeeDao {
	List<Employee> selectEmployeeByAll();
	Employee selectEmployeeByNo(Employee employee);
	
	int insertEmployee(Employee employee);
	int updateEmployee(Employee employee);
	int deleteEmployee(Employee employee);
	
	List<Employee> selectEmployeeByTitle(Title title);
}






