package erp_teacher.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import erp_teacher.dto.Employee;
import erp_teacher.dto.EmployeeDetail;
import erp_teacher.service.EmployeeService;
import erp_teacher.ui.content.AbstractContentPanel;
import erp_teacher.ui.content.EmployeeDetailPanel;
import erp_teacher.ui.content.EmployeePanel;
import erp_teacher.ui.list.AbstractCustomTablePanel;
import erp_teacher.ui.list.EmployeeTablePanel;

@SuppressWarnings("serial")
public class EmployeeManagerUI extends AbstractManagerUI<Employee> {
	private EmployeeService service;
	
	public EmployeeManagerUI() {
	}
	
	@Override
	protected void setService() {
		service = new EmployeeService();
	}

	@Override
	protected void tableLoadData() {
		((EmployeeTablePanel)pList).setService(service);
		pList.loadData();		
	}

	@Override
	protected AbstractContentPanel<Employee> createContentPanel() {
		EmployeePanel empPanel = new EmployeePanel();
		empPanel.setService(service);
		return empPanel;
	}

	@Override
	protected AbstractCustomTablePanel<Employee> createTablePanel() {
		return new EmployeeTablePanel();
	}

	@Override
	protected void actionPerformedMenuGubun() {
		Employee item = pList.getItem();
//		System.out.println("item " + item);
//		System.out.println("service " + service);
		
		EmployeeDetail empDetail = service.showEmpDetailByEmpNo(item);
//		System.out.println("empDetail " + empDetail);
		if (empDetail == null) {
			JOptionPane.showMessageDialog(null, "해당 정보가 없음");
			return;
		}
		EmployeeDetailPanel subDetailPanel = new EmployeeDetailPanel();
		subDetailPanel.setItem(empDetail);
		
		JFrame empDetailFrame = new JFrame("사원 세부정보");
		empDetailFrame.setBounds(10, 10, 450, 600);
		empDetailFrame.add(subDetailPanel, BorderLayout.CENTER);
		empDetailFrame.setVisible(true);
	}

	@Override
	protected void actionPerformedMenuUpdate() {
		Employee updateEmp = pList.getItem();
		pContent.setItem(updateEmp);
		btnAdd.setText("수정");			
	}

	@Override
	protected void actionPerformedMenuDelete() {
		Employee delEmp = pList.getItem();
		service.removeEmployee(delEmp);
		pList.loadData();
		JOptionPane.showMessageDialog(null, delEmp + "삭제 되었습니다.");				
	}

	@Override
	protected void actionPerformedBtnUpdate(ActionEvent e) {
		Employee updateEmp = pContent.getItem();
		service.modifyEmployee(updateEmp);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateEmp.getEmpName() + "정보가 수정되었습니다.");				
	}

	@Override
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee empl = pContent.getItem();
		service.addEmployee(empl);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, empl.getEmpName() + " 추가했습니다.");				
	}

}
