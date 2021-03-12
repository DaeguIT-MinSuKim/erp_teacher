package erp_teacher.service;

import java.util.List;

import erp_teacher.dao.TitleDao;
import erp_teacher.dao.impl.TitleDaoImpl;
import erp_teacher.dto.Title;

public class TitleService {
	private TitleDao dao = TitleDaoImpl.getInstance();
	
	public List<Title> showTitles(){
		return dao.selectTitleByAll();
	}
	
	public void addTitle(Title title) {
		dao.insertTitle(title);
	}
	
	public void removeTitle(Title title) {
		dao.deleteTitle(title.gettNo());
	}
}
