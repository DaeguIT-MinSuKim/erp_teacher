package erp_teacher.service;

import java.util.List;

import erp_teacher.dao.DepartmentDao;
import erp_teacher.dao.EmployeeDao;
import erp_teacher.dao.impl.DepartmentDaoImpl;
import erp_teacher.dao.impl.EmployeeDaoImpl;
import erp_teacher.dto.Department;
import erp_teacher.dto.Employee;
import erp_teacher.dto.Title;

public class DepartmentService {
	private DepartmentDao dao = DepartmentDaoImpl.getInstance();
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();
	
	public List<Department> showDeptList(){
		return dao.selectDepartmentByAll();
	}
	
	public void addDepartment(Department dept) {
		dao.insertDepartment(dept);
	}
	
	public void removeDepartment(Department dept) {
		dao.deleteDepartment(dept.getDeptNo());
	}
	
	public void modifyDepartment(Department dept) {
		dao.updateDepartment(dept);
	}
	
	public List<Employee> showEmployeeGroupByDepartment(Department dept){
		return empDao.selectEmployeeByDept(dept);
	}
}
