package erp_teacher.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp_teacher.dto.Employee;
import erp_teacher.dto.Title;
import erp_teacher.service.TitleService;
import erp_teacher.ui.content.TitlePanel;
import erp_teacher.ui.exception.InvalidCheckException;
import erp_teacher.ui.exception.SqlConstraintException;
import erp_teacher.ui.list.TitleTablePanel;

@SuppressWarnings("serial")
public class TitleManager extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private JButton btnClear;
	   
	private TitlePanel pContent;
	private TitleTablePanel pList;
	private TitleService service;
	
	public TitleManager() {
		initialize();
		
		service = new TitleService();
	    pList.setService(service);
        pList.loadData();
	}
	
	private void initialize() {
		setTitle("직책 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pContent = new TitlePanel();
		contentPane.add(pContent);
		
		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		btnClear = new JButton("취소");
		btnClear.addActionListener(this);
		pBtns.add(btnClear);
		
		pList = new TitleTablePanel();

		contentPane.add(pList);
		
		JPopupMenu popupMenu = createPopupMenu();
		pList.setPopupMenu(popupMenu);
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(this);
		popMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(this);
		popMenu.add(deleteItem);
		
		JMenuItem empListByTitleItem = new JMenuItem("동일 직책 사원 보기");
		empListByTitleItem.addActionListener(this);
		popMenu.add(empListByTitleItem);
		
		return popMenu;
	}
	
	public void actionPerformed(ActionEvent e) {
	    try {
    		if (e.getSource() == btnClear) {
    			actionPerformedBtnClear(e);
    		}
		
			if (e.getSource() == btnAdd) {
				if (e.getActionCommand().contentEquals("추가")) {
					actionPerformedBtnAdd(e);
				}else {
					actionPerformedBtnUpdate(e);
				}
			}
			
			if (e.getSource() instanceof JMenuItem) {
			
    			if (e.getActionCommand().equals("삭제")) {
                    actionPerformedMenuDelete();
                }
                
                if (e.getActionCommand().equals("수정")) {
                    actionPerformedMenuUpdate();
                }
                
                if (e.getActionCommand().contentEquals("동일 직책 사원 보기")) {
                    actionPerformedMenuEqualsItem();
                }
			}
            
		}catch (InvalidCheckException | SqlConstraintException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
//			pContent.clearTf();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void actionPerformedBtnUpdate(ActionEvent e) {
		//pContent에서 수정된 title 가져오기
		//update 수행
		//pList 갱신
		//pContent clearTf()호출하여 초기화
		//btnAdd 텍스트 변경 수정->추가
		
		Title updateTitle = pContent.getItem();
		service.modifyTitle(updateTitle);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateTitle.gettName() + "정보가 수정되었습니다.");
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Title title = pContent.getItem();
		service.addTitle(title);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, title + " 추가했습니다.");
	}
	
	protected void actionPerformedBtnClear(ActionEvent e) {
		pContent.clearTf();
		
		if (btnAdd.getText().contentEquals("수정")) {
			btnAdd.setText("추가");
		}
	}
	
	protected void actionPerformedMenuEqualsItem() {
        /*
         * 1. EmployeeDao -> selectEmployeeByTitle() 추가
         * 2. EmployeeDaoImpl -> selectEmployeeByTitle() 구현
         * 3. EmployeeDaoTest -> Test하기
         * 4. TitleService -> EmployeeDaoImpl field 추가 및 메서드 추가
         * 5. 아래 기능 추가
         * 6. 예외찾아서 추가하기 (신규 직책 추가 시 NullPointException)
         */
        Title title = pList.getItem();
        List<Employee> list = service.showEmployeeGroupByTitle(title);
        
        if (list == null) {
            JOptionPane.showMessageDialog(null, "해당 사원이 없음", "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        List<String> strList = list
                .parallelStream()
                .map( s->{ return String.format("%s(%d)", s.getEmpName(), s.getEmpNo()); })
                .collect(Collectors.toList());
        
        JOptionPane.showMessageDialog(null, strList, "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
    }

	protected void actionPerformedMenuUpdate() {
        Title updateTitle = pList.getItem();
        pContent.setItem(updateTitle);
        btnAdd.setText("수정");
    }

	protected void actionPerformedMenuDelete() {
        Title delTitle = pList.getItem();
        service.removeTitle(delTitle);
        pList.loadData();
        JOptionPane.showMessageDialog(null, delTitle + "삭제 되었습니다.");
    }
}
