package erp_teacher.ui;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import erp_teacher.dto.Employee;
import erp_teacher.service.EmployeeService;
import erp_teacher.ui.content.AbstractContent;
import erp_teacher.ui.content.EmployeePanel;
import erp_teacher.ui.list.AbstractCustomTablePanel;
import erp_teacher.ui.list.EmployeeTablePanel;

@SuppressWarnings("serial")
public class EmployeeManagerUI extends AbstractManager<Employee> {
    private EmployeeService service;
    
    @Override
    protected void setService() {
        service = new EmployeeService();        
    }
    
    @Override
    protected void tableLoadData() {
       
        ((EmployeeTablePanel) pList).setService(service);
        pList.loadData();        
    }

    @Override
    protected AbstractCustomTablePanel<Employee> createTable() {
        return new EmployeeTablePanel();
    }

    @Override
    protected AbstractContent<Employee> createContent() {
        EmployeePanel panel = new EmployeePanel();
        panel.setService(service);
        return panel;
    }

    @Override
    protected void actionPerformedBtnUpdate(ActionEvent e) {
        Employee updateItem = pContent.getItem();
        service.modifyEmployee(updateItem);
        pList.loadData();
        pContent.clearTf();
        btnAdd.setText("추가");
        JOptionPane.showMessageDialog(null, updateItem.getEmpName() + "정보가 수정되었습니다.");                
    }

    @Override
    protected void actionPerformedBtnAdd(ActionEvent e) {
        Employee item = pContent.getItem();
        service.addEmployee(item);
        pList.loadData();
        pContent.clearTf();
        JOptionPane.showMessageDialog(null, item + " 추가했습니다.");        
    }

    @Override
    protected void actionPerformedMenuEqualsItem() {
        throw new UnsupportedOperationException("구현하지 않음");
    }

    @Override
    protected void actionPerformedMenuUpdate() {
        Employee updateItem = pList.getItem();
        pContent.setItem(updateItem);
        btnAdd.setText("수정");        
    }

    @Override
    protected void actionPerformedMenuDelete() {
        Employee delItem = pList.getItem();
        service.removeEmployee(delItem);
        pList.loadData();
        JOptionPane.showMessageDialog(null, delItem + "삭제 되었습니다.");        
    }



}
