package erp_teacher.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import erp_teacher.dao.EmployeeDetailDao;
import erp_teacher.database.JdbcConn;
import erp_teacher.dto.Employee;
import erp_teacher.dto.EmployeeDetail;

public class EmployeeDetailDaoImpl implements EmployeeDetailDao {
    private static EmployeeDetailDaoImpl instance = new EmployeeDetailDaoImpl();

    public static EmployeeDetailDaoImpl getInstance() {
        if (instance == null) {
            instance = new EmployeeDetailDaoImpl();
        }
        return instance;
    }

    @Override
    public EmployeeDetail selectEmployeeDetailByNo(Employee employee) {
        String sql = "SELECT empno, pic, gender, hiredate" + "  FROM emp_detail" + " WHERE empno = ?";
        try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, employee.getEmpNo());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return getEmployeeDetail(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private EmployeeDetail getEmployeeDetail(ResultSet rs) throws SQLException {
        // empno, pic, gender, hiredate
        int empNo = rs.getInt("empno");
        boolean gender = rs.getBoolean("gender");
        Date hireDate = rs.getTimestamp("hiredate");
        String pass = null;
        byte[] pic = rs.getBytes("pic");
        return new EmployeeDetail(empNo, gender, hireDate, pass, pic);
    }

    @Override
    public int insertEmployeeDetail(EmployeeDetail empDetail) {
        String sql = "INSERT INTO emp_detail (empno, pic, gender, hiredate, pass) VALUES(?, ?, ?, ?, password(?))";
        try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, empDetail.getEmpNo());
            pstmt.setBytes(2, empDetail.getPic());
            pstmt.setBoolean(3, empDetail.isGender());
            pstmt.setTimestamp(4, new Timestamp(empDetail.getHireDate().getTime()));
            pstmt.setString(5, empDetail.getPass());
//          util.Date->sql.Date로 변환

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateEmployeeDetail(EmployeeDetail empDetail) {
        String sql = "UPDATE emp_detail"
                +    "   SET pic=?, gender=?, hiredate=?, pass=?"  
                +    " WHERE empno=?";
        try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setBytes(1, empDetail.getPic());
            pstmt.setBoolean(2, empDetail.isGender());
            pstmt.setTimestamp(3, new Timestamp(empDetail.getHireDate().getTime()));
            pstmt.setString(4, empDetail.getPass());
            pstmt.setInt(5, empDetail.getEmpNo());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteEmployeeDetail(Employee employee) {
        String sql = "delete " + "  from emp_detail " + " where empno = ?";
        try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getEmpNo());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
