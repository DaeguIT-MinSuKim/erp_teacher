package erp_teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import erp_teacher.ui.list.TitleTablePanel;
import java.awt.GridLayout;
import erp_teacher.ui.list.DepartmentTablePanel;
import javax.swing.BoxLayout;

import erp_teacher.service.EmployeeService;
import erp_teacher.ui.content.EmployeePanel;
import javax.swing.JButton;

public class TestFrame extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestFrame() {
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		EmployeeService service = new EmployeeService();
		EmployeePanel pEmpItem = new EmployeePanel();
		pEmpItem.setService(service);
		
		contentPane.add(pEmpItem);
		
		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);
		
		JButton btnAdd = new JButton("추가");
		pBtns.add(btnAdd);
		
		JButton btnCancel = new JButton("취소");
		pBtns.add(btnCancel);
	}

}
