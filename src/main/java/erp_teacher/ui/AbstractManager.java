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
import erp_teacher.ui.content.DepartmentPanel;
import erp_teacher.ui.content.EmployeePanel;
import erp_teacher.ui.content.InterfaceItem;
import erp_teacher.ui.content.TitlePanel;
import erp_teacher.ui.exception.InvalidCheckException;
import erp_teacher.ui.exception.NotSelectedException;
import erp_teacher.ui.exception.SqlConstraintException;
import erp_teacher.ui.list.AbstractCustomTablePanel;
import erp_teacher.ui.list.DepartmentTablePanel;
import erp_teacher.ui.list.EmployeeTablePanel;
import erp_teacher.ui.list.TitleTablePanel;

@SuppressWarnings("serial")
public abstract class AbstractManager<T> extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JButton btnAdd;
	
	private InterfaceItem<T> pContent;
	private AbstractCustomTablePanel<T> pList;
	
	private ManagerType type;
//	private TitleService service;
	
	public AbstractManager(ManagerType type) {
		this.type = type;
//		service = new TitleService();
		initialize();
	}
	
	private void initialize() {
		setTitle("직책 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pContent = getItemPanel(type);
		contentPane.add(pContent);
		
		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		JButton btnClear = new JButton("취소");
		pBtns.add(btnClear);
		
		pList = getTitleList(type);
		setService();
//		((TitleTablePanel)pList).setService(service);
		pList.loadData();
		contentPane.add(pList);
		
		JPopupMenu popupMenu = createPopupMenu();
		pList.setPopupMenu(popupMenu);
	}

	/**
	 * ((TitleTablePanel)pList).setService(service);
	 */
	public abstract void setService();

	@SuppressWarnings("unchecked")
	private AbstractCustomTablePanel<T> getTitleList(ManagerType type) {
		switch (type) {
		case EMP:
			pList =  (AbstractCustomTablePanel<T>) new EmployeeTablePanel();
			break;

		case DEPT:
			pList = (AbstractCustomTablePanel<T>) new DepartmentTablePanel();
			break;
			
		case TITLE:
			pList = (AbstractCustomTablePanel<T>) new TitleTablePanel();
			break;
		default:
			pList = (AbstractCustomTablePanel<T>) new EmployeeTablePanel();
		}
		return pList;
	}

	@SuppressWarnings("unchecked")
	private InterfaceItem<T> getItemPanel(ManagerType type) {
		switch (type) {
		case EMP:
			pContent = (InterfaceItem<T>) new EmployeePanel();
			break;

		case DEPT:
			pContent = (InterfaceItem<T>) new DepartmentPanel();
			break;
			
		case TITLE:
			pContent = (InterfaceItem<T>) new TitlePanel();
			break;
		default:
			pContent = (InterfaceItem<T>) new EmployeePanel();
		}
		return pContent;
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(popupMenuListner);
		popMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(popupMenuListner);
		popMenu.add(deleteItem);
		
		JMenuItem empListByTitleItem = new JMenuItem("동일 직책 사원 보기");
		empListByTitleItem.addActionListener(popupMenuListner);
		popMenu.add(empListByTitleItem);
		
		return popMenu;
	}
	
	ActionListener popupMenuListner = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (e.getActionCommand().equals("삭제")) {
					T delItem = pList.getItem();
//					service.removeTitle(delItem);
					removeItemService();
					pList.loadData();
					JOptionPane.showMessageDialog(null, delItem + "삭제 되었습니다.");
				}
				
				if (e.getActionCommand().equals("수정")) {
					T updateItem = pList.getItem();
					pContent.setItem(updateItem);
					btnAdd.setText("수정");
				}
				
				if (e.getActionCommand().contentEquals("동일 직책 사원 보기")) {
					/*
					 * 1. EmployeeDao -> selectEmployeeByTitle() 추가
					 * 2. EmployeeDaoImpl -> selectEmployeeByTitle() 구현
					 * 3. EmployeeDaoTest -> Test하기
					 * 4. TitleService -> EmployeeDaoImpl field 추가 및 메서드 추가
					 * 5. 아래 기능 추가
					 * 6. 예외찾아서 추가하기 (신규 직책 추가 시 NullPointException)
					 */
					showEqualsByGuBun();
					/*
					 * T title = pList.getItem(); List<Employee> list =
					 * service.showEmployeeGroupByTitle(title);
					 * 
					 * if (list == null) { JOptionPane.showMessageDialog(null, "해당 사원이 없음",
					 * "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE); return; }
					 * 
					 * List<String> strList = list .parallelStream() .map( s->{ return
					 * String.format("%s(%d)", s.getEmpName(), s.getEmpNo()); })
					 * .collect(Collectors.toList());
					 * 
					 * JOptionPane.showMessageDialog(null, strList, "동일 직책 사원",
					 * JOptionPane.INFORMATION_MESSAGE);
					 */
				}
				
			}catch (NotSelectedException | SqlConstraintException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		
	};
	
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnAdd) {
				if (e.getActionCommand().contentEquals("추가")) {
					actionPerformedBtnAdd(e);
				}else {
					actionPerformedBtnUpdate(e);
				}
			}
		}catch (InvalidCheckException | SqlConstraintException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
//			pContent.clearTf();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	protected abstract void showEqualsByGuBun();

	private void actionPerformedBtnUpdate(ActionEvent e) {
		//pContent에서 수정된 title 가져오기
		//update 수행
		//pList 갱신
		//pContent clearTf()호출하여 초기화
		//btnAdd 텍스트 변경 수정->추가
		
		T updateItem = pContent.getItem();
//		service.modifyTitle(updateItem);
		modifyItemService(updateItem);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateItem + "정보가 수정되었습니다.");
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		T item = pContent.getItem();
//		service.addTitle(item);
		addItemService(item);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, item + " 추가했습니다.");
	}
	
	/**
	 * service.removeTitle(delItem);
	 */
	public abstract void removeItemService();

	public abstract void addItemService(T item);
	
	public abstract void modifyItemService(T item);
}
