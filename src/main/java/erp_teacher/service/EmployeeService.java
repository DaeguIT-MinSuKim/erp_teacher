package erp_teacher.service;

import java.util.List;

import erp_teacher.dao.DepartmentDao;
import erp_teacher.dao.EmployeeDao;
import erp_teacher.dao.TitleDao;
import erp_teacher.dao.impl.DepartmentDaoImpl;
import erp_teacher.dao.impl.EmployeeDaoImpl;
import erp_teacher.dao.impl.TitleDaoImpl;
import erp_teacher.dto.Department;
import erp_teacher.dto.Employee;
import erp_teacher.dto.Title;

public class EmployeeService {
	private DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
	private TitleDao titleDao = TitleDaoImpl.getInstance();
	private EmployeeDao employeeDao = EmployeeDaoImpl.getInstance();
	
	public List<Department> showDeptList(){
		return deptDao.selectDepartmentByAll();
	}
	
	public List<Title> showTitleList(){
		return titleDao.selectTitleByAll();
	}
	
	public List<Employee> showEmployeeByDept(Department dept){
		return employeeDao.selectEmployeeByDept(dept);
	}
	
	public List<Employee> showEmployees(){
		return employeeDao.selectEmployeeByAll();
	}
}
