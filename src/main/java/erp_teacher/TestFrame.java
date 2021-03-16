package erp_teacher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp_teacher.dto.EmployeeDetail;
import erp_teacher.service.EmployeeService;
import erp_teacher.ui.content.EmployeeDetailPanel;
import erp_teacher.ui.list.EmployeeTablePanel;

@SuppressWarnings("serial")
public class TestFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private EmployeeTablePanel pList;
	private EmployeeDetailPanel panel;
	private JPanel panel_1;
	private JButton btnAdd;
	private JButton btnGet;
	private JButton btnClear;

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
		setBounds(100, 100, 450, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		EmployeeService service = new EmployeeService();
		
		pList = new EmployeeTablePanel();
		pList.setService(service);
		pList.loadData();
		contentPane.add(pList);
		
		panel = new EmployeeDetailPanel();
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		panel_1.add(btnAdd);
		
		btnGet = new JButton("가져오기");
		btnGet.addActionListener(this);
		panel_1.add(btnGet);
		
		btnClear = new JButton("취소");
		panel_1.add(btnClear);
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			actionPerformedBtnAdd(e);
		}
		if (e.getSource() == btnGet) {
			actionPerformedBtnGet(e);
		}
	}
	
	protected void actionPerformedBtnGet(ActionEvent e) {
		EmployeeDetail empDetail =  panel.getItem();
		JOptionPane.showMessageDialog(null, empDetail);
	}
	
	protected void actionPerformedBtnAdd(ActionEvent e) {
		panel.setItem(new EmployeeDetail(1003, true, new Date(), getImage("suji.jpg")));
	}
	
	private byte[] getImage(String imgName) {
		byte[] pic = null;
		            //     /images/imgName
		File file = new File(System.getProperty("user.dir") + File.separator + "images", imgName);
		try(InputStream is = new FileInputStream(file)){
			pic = new byte[is.available()]; //file로 부터 읽은 이미지의 바이트길이로 배열 생성
			is.read(pic);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}
}




