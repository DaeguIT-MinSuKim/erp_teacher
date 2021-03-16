package erp_teacher.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import erp_teacher.dao.EmployeeDetailDao;
import erp_teacher.database.JdbcConn;
import erp_teacher.dto.Employee;
import erp_teacher.dto.EmployeeDetail;
import erp_teacher.ui.exception.SqlConstraintException;

public class EmployeeDetailDaoImpl implements EmployeeDetailDao {
	private static EmployeeDetailDaoImpl instance = new EmployeeDetailDaoImpl();

	public static EmployeeDetailDaoImpl getInstance() {
		if (instance == null) {
			instance = new EmployeeDetailDaoImpl();
		}
		return instance;
	}

	private EmployeeDetailDaoImpl() {}
	
	@Override
	public EmployeeDetail selectEmployeeDetailByNo(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertEmployeeDetail(EmployeeDetail empDetail) {
		String sql = "INSERT INTO emp_detail(empno, pic, gender, hiredate, pass)" 
	              + " VALUES (?, ?, ?, ?, password(?))";
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, empDetail.getEmpNo());
			pstmt.setBytes(2, empDetail.getPic());
			pstmt.setBoolean(3, empDetail.isGender());
			//util.Date -> sql.Date로 변환
			pstmt.setTimestamp(4, new Timestamp(empDetail.getHireDate().getTime()));
			pstmt.setString(5, empDetail.getPass());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage(), e);
		}
	}

	@Override
	public int updateEmployeeDetail(EmployeeDetail empDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmployeeDetail(Employee employee) {
		// TODO Auto-generated method stub
		return 0;
	}

}
