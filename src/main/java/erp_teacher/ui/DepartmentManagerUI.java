package erp_teacher.ui;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import erp_teacher.dto.Department;
import erp_teacher.dto.Employee;
import erp_teacher.service.DepartmentService;
import erp_teacher.service.EmployeeService;
import erp_teacher.ui.content.AbstractContent;
import erp_teacher.ui.content.DepartmentPanel;
import erp_teacher.ui.list.AbstractCustomTablePanel;
import erp_teacher.ui.list.DepartmentTablePanel;

@SuppressWarnings("serial")
public class DepartmentManagerUI extends AbstractManager<Department> {
    private DepartmentService service;
    
    @Override
    protected void setService() {
        service = new DepartmentService();
    }
    
    @Override
    protected void tableLoadData() {
        ((DepartmentTablePanel) pList).setService(service);
        pList.loadData();
    }

    @Override
    protected AbstractCustomTablePanel<Department> createTable() {
        return new DepartmentTablePanel();
    }

    @Override
    protected AbstractContent<Department> createContent() {
        return new DepartmentPanel();
    }

    @Override
    protected void actionPerformedBtnUpdate(ActionEvent e) {
        // pContent에서 수정된 title 가져오기
        // update 수행
        // pList 갱신
        // pContent clearTf()호출하여 초기화
        // btnAdd 텍스트 변경 수정->추가

        Department updateDept = pContent.getItem();
        service.modifyDepartment(updateDept);
        pList.loadData();
        pContent.clearTf();
        btnAdd.setText("추가");
        JOptionPane.showMessageDialog(null, updateDept.getDeptName() + "정보가 수정되었습니다.");        
    }

    @Override
    protected void actionPerformedBtnAdd(ActionEvent e) {
        Department addDept = pContent.getItem();
        service.addDepartment(addDept);
        pList.loadData();
        pContent.clearTf();
        JOptionPane.showMessageDialog(null, addDept + " 추가했습니다.");        
    }

    @Override
    protected void actionPerformedMenuEqualsItem() {
        /*
         * 1. EmployeeDao -> selectEmployeeByTitle() 추가 2. EmployeeDaoImpl ->
         * selectEmployeeByTitle() 구현 3. EmployeeDaoTest -> Test하기 4. TitleService ->
         * EmployeeDaoImpl field 추가 및 메서드 추가 5. 아래 기능 추가 6. 예외찾아서 추가하기 (신규 직책 추가 시
         * NullPointException)
         */
        Department dept = pList.getItem();
        List<Employee> list = service.showEmployeeGroupByDepartment(dept);

        if (list == null) {
            JOptionPane.showMessageDialog(null, "해당 사원이 없음", "동일 부서 사원", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<String> strList = list.parallelStream().map(s -> {
            return String.format("%s(%d)", s.getEmpName(), s.getEmpNo());
        }).collect(Collectors.toList());

        JOptionPane.showMessageDialog(null, strList, "동일 부서 사원", JOptionPane.INFORMATION_MESSAGE);        
    }

    @Override
    protected void actionPerformedMenuUpdate() {
        Department updateDept = pList.getItem();
        pContent.setItem(updateDept);
        btnAdd.setText("수정");        
    }

    @Override
    protected void actionPerformedMenuDelete() {
        Department delDept = pList.getItem();
        service.removeDepartment(delDept);
        pList.loadData();
        JOptionPane.showMessageDialog(null, delDept + "삭제 되었습니다.");        
    }

}
