package erp_teacher.service;

import java.util.List;

import erp_teacher.dao.EmployeeDao;
import erp_teacher.dao.TitleDao;
import erp_teacher.dao.impl.EmployeeDaoImpl;
import erp_teacher.dao.impl.TitleDaoImpl;
import erp_teacher.dto.Employee;
import erp_teacher.dto.Title;

public class TitleService {
	private TitleDao dao = TitleDaoImpl.getInstance();
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();
	
	public List<Title> showTitles(){
		return dao.selectTitleByAll();
	}
	
	public void addTitle(Title title) {
		dao.insertTitle(title);
	}
	
	public void removeTitle(Title title) {
		dao.deleteTitle(title.gettNo());
	}
	
	public void modifyTitle(Title title) {
		dao.updateTitle(title);
	}
	
	public List<Employee> showEmployeeGroupByTitle(Title title){
		return empDao.selectEmployeeByTitle(title);
	}
}
