package erp_teacher.service;

import erp_teacher.dao.EmployeeDetailDao;
import erp_teacher.dao.impl.EmployeeDetailDaoImpl;
import erp_teacher.dto.Employee;
import erp_teacher.dto.EmployeeDetail;

public class EmployeeDetailService {
	private EmployeeDetailDao empDetailDao = EmployeeDetailDaoImpl.getInstance();
	
	public EmployeeDetail selectEmployeeDetailByEmpNo(Employee employee) {
		return empDetailDao.selectEmployeeDetailByNo(employee);
	}
	
	public void addEmployeeDetail(EmployeeDetail empDetail) {
		empDetailDao.insertEmployeeDetail(empDetail);
	}
	
	public void modifyEmployeeDetail(EmployeeDetail empDetail) {
		empDetailDao.updateEmployeeDetail(empDetail);
	}
	
	public void removeEmployeeDetail(Employee employee) {
		empDetailDao.deleteEmployeeDetail(employee);
	}
	
}
