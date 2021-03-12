package erp_teacher.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp_teacher.dto.Title;
import erp_teacher.ui.exception.InvalidCheckException;

@SuppressWarnings("serial")
public class TitlePanel extends JPanel {
	private JTextField tfNo;
	private JTextField tfName;

	public TitlePanel() {
		initialize();
	}
	
	private void initialize() {
		setBorder(new TitledBorder(null, "직책정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 0));
		
		JLabel lblNo = new JLabel("직책번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNo);
		
		tfNo = new JTextField();
		tfNo.setColumns(10);
		add(tfNo);
		
		JLabel lblName = new JLabel("직책명");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		add(tfName);
	}

	public void setTitle(Title title) {
		tfNo.setText(String.valueOf(title.gettNo()));
		tfName.setText(title.gettName());
		
		tfNo.setEditable(false);
	}
	
	public Title getTitle() {
		validCheck();
		int tNo = Integer.parseInt(tfNo.getText().trim());
		String tName = tfName.getText().trim();
		return new Title(tNo, tName);
	}
	
	private void validCheck() {
		if (tfNo.getText().contentEquals("") || tfName.getText().equals("")) {
			throw new InvalidCheckException();
		}
	}

	public void clearTf() {
		tfNo.setText("");
		tfName.setText("");
		
		if (!tfNo.isEditable()) {
			tfNo.setEditable(true);
		}
	}
}
