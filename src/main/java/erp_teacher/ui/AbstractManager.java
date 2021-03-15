package erp_teacher.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp_teacher.dto.Department;
import erp_teacher.ui.content.AbstractContent;
import erp_teacher.ui.exception.InvalidCheckException;
import erp_teacher.ui.exception.SqlConstraintException;
import erp_teacher.ui.list.AbstractCustomTablePanel;

@SuppressWarnings("serial")
public abstract class AbstractManager<T> extends JFrame implements ActionListener {

    private JPanel contentPane;
    protected JButton btnAdd;
    protected JButton btnClear;

    protected AbstractContent<T> pContent;
    protected AbstractCustomTablePanel<T> pList;

    public AbstractManager() {
        setService();
        
        initialize();
        
        tableLoadData();
    }

    protected abstract void setService();

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        pContent = createContent();
        contentPane.add(pContent);

        JPanel pBtns = new JPanel();
        contentPane.add(pBtns);

        btnAdd = new JButton("추가");
        btnAdd.addActionListener(this);
        pBtns.add(btnAdd);

        btnClear = new JButton("취소");
        btnClear.addActionListener(this);
        pBtns.add(btnClear);

        pList = createTable();
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
            if (e.getSource() instanceof JButton) {
                if (e.getSource() == btnClear) {
                    actionPerformedBtnClear(e);
                }

                if (e.getSource() == btnAdd) {
                    if (e.getActionCommand().contentEquals("추가")) {
                        actionPerformedBtnAdd(e);
                    } else {
                        actionPerformedBtnUpdate(e);
                    }
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

        } catch (InvalidCheckException | SqlConstraintException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    
    protected void actionPerformedBtnClear(ActionEvent e) {
        pContent.clearTf();

        if (btnAdd.getText().contentEquals("수정")) {
            btnAdd.setText("추가");
        }
    }
    
    protected void actionPerformedMenuUpdate() {
        T updateItem = pList.getItem();
        pContent.setItem(updateItem);
        btnAdd.setText("수정");        
    }
    
    protected abstract void tableLoadData();

    protected abstract AbstractCustomTablePanel<T> createTable();

    protected abstract AbstractContent<T> createContent();

    protected abstract void actionPerformedBtnUpdate(ActionEvent e);

    protected abstract void actionPerformedBtnAdd(ActionEvent e);

    protected abstract void actionPerformedMenuEqualsItem();

    protected abstract void actionPerformedMenuDelete();

}
