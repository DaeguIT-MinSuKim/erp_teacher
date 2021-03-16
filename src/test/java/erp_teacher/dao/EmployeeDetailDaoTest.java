package erp_teacher.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp_teacher.dao.impl.EmployeeDetailDaoImpl;
import erp_teacher.dto.Employee;
import erp_teacher.dto.EmployeeDetail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDetailDaoTest {
	private EmployeeDetailDao dao = EmployeeDetailDaoImpl.getInstance();
	
	@After
	public void tearDown() throws Exception {
		System.out.println();
	}

	@Test
	public void test02SelectEmployeeDetailByNo() {
		System.out.printf("%s()%n", "test02SelectEmployeeDetailByNo");
		
		EmployeeDetail employeeDetail = dao.selectEmployeeDetailByNo(new Employee(1003));
		Assert.assertNotNull(employeeDetail);
		
		System.out.println(employeeDetail);
	}

	@Test
	public void test01InsertEmployeeDetail() {
		System.out.printf("%s()%n", "test01InsertEmployeeDetail");
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		
//		EmployeeDetail empDetail = new EmployeeDetail(1003, true, cal.getTime(), "1234", getImage("noImg.jpg"));
		EmployeeDetail empDetail = new EmployeeDetail(1003, true, new Date(), "1234", getImage("noImg.jpg"));
		int res = dao.insertEmployeeDetail(empDetail);
		
		Assert.assertEquals(1, res);
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

	@Test
	public void test03UpdateEmployeeDetail() {
		System.out.printf("%s()%n", "test03UpdateEmployeeDetail");
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		
		EmployeeDetail empDetail = new EmployeeDetail(1003, false, cal.getTime(), "1234", getImage("suji.jpg"));
		int res = dao.updateEmployeeDetail(empDetail);
		
		Assert.assertEquals(1, res);
	}

//	@Test
	public void test04DeleteEmployeeDetail() {
		System.out.printf("%s()%n", "test04DeleteEmployeeDetail");
		Employee employee = new Employee(1003);
		int res = dao.deleteEmployeeDetail(employee);
		
		Assert.assertEquals(1, res);
	}

}
