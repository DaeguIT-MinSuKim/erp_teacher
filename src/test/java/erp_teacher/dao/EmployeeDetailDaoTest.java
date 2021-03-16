package erp_teacher.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
        System.out.printf("%s()%n", "testInsertEmployeeDetail");

        EmployeeDetail detail = dao.selectEmployeeDetailByNo(new Employee(1003));
        Assert.assertNotNull(detail);
        System.out.println(detail);
    }

    @Test
    public void test01InsertEmployeeDetail() {
        System.out.printf("%s()%n", "testInsertEmployeeDetail");

        EmployeeDetail empDetail = new EmployeeDetail(1003, true, new Date(), "test", getImage("noImg.jpg"));
        int res = dao.insertEmployeeDetail(empDetail);
        Assert.assertEquals(1, res);

    }

    @Test
    public void test03UpdateEmployeeDetail() {
        fail("Not yet implemented");
    }

    @Test
    public void test04DeleteEmployeeDetail() {
        System.out.printf("%s()%n", "test03DeleteEmployeeDetail");

        Employee employee = new Employee(1003);
        int res = dao.deleteEmployeeDetail(employee);
        
        Assert.assertEquals(1, res);
    }

    private byte[] getImage(String imgName) {
        byte[] pic = null;
        File file = new File(System.getProperty("user.dir") + File.separator + "images", imgName);
        try (InputStream is = new FileInputStream(file)) {
            pic = new byte[is.available()];
            is.read(pic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pic;
    }

}
