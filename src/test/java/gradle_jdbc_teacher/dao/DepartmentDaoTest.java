package gradle_jdbc_teacher.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gradle_jdbc_teacher.dao.impl.DepartmentDaoImpl;
import gradle_jdbc_teacher.dto.Department;
import gradle_jdbc_teacher.util.LogUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private DepartmentDao dao;
	@Before
	public void setUp() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = DepartmentDaoImpl.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = null;
	} 

	@Test
	public void test01SelectDepartmentByNo() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department department = dao.selectDepartmentByNo(new Department(1));
		Assert.assertNotNull(department);
		LogUtil.prnLog(department);
	}

	@Test
	public void test02SelectDepartmentByAll() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Department> list = dao.selectDepartmentByAll();
		Assert.assertNotNull(list);
		for(Department d : list) LogUtil.prnLog(d);
	}

	@Test
	public void test03InsertDepartment() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = dao.insertDepartment(new Department(5, "마케팅", 30));
		Assert.assertEquals(1, res);
		for(Department d : dao.selectDepartmentByAll())LogUtil.prnLog(d);
	}

	@Test
	public void test04UpdateDepartment() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = dao.updateDepartment(new Department(5, "모바일개발", 60));
		Assert.assertEquals(1, res);
		for(Department d : dao.selectDepartmentByAll())LogUtil.prnLog(d);
	}

	@Test
	public void test05DeleteDepartment() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = dao.deleteDepartment(new Department(5));
		Assert.assertEquals(1, res);
		for(Department d : dao.selectDepartmentByAll())LogUtil.prnLog(d);
	}

}
