package erp_teacher.dao;

import erp_teacher.dto.Employee;
import erp_teacher.dto.EmployeeDetail;

public interface EmployeeDetailDao {
    EmployeeDetail selectEmployeeDetailByNo(Employee employee);
	
	int insertEmployeeDetail(EmployeeDetail empDetail);
	int updateEmployeeDetail(EmployeeDetail empDetail);
	int deleteEmployeeDetail(Employee employee);

}






