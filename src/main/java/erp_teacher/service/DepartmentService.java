package erp_teacher.service;

import java.util.List;

import erp_teacher.dao.DepartmentDao;
import erp_teacher.dao.impl.DepartmentDaoImpl;
import erp_teacher.dto.Department;

public class DepartmentService {
	private DepartmentDao dao = DepartmentDaoImpl.getInstance();
	
	public List<Department> showDeptList(){
		return dao.selectDepartmentByAll();
	}
}
