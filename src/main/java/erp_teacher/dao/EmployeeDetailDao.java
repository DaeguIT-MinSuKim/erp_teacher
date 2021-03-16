package erp_teacher.dao;

import erp_teacher.dto.Employee;

public interface EmployeeDetailDao {
	Employee selectEmployeeDetailByNo(Employee employee);
	
	int insertEmployeeDetail(Employee employee);
	int updateEmployeeDetail(Employee employee);
	int deleteEmployeeDetail(Employee employee);

}






