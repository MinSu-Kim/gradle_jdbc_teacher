package gradle_jdbc_teacher.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gradle_jdbc_teacher.dao.EmployeeDao;
import gradle_jdbc_teacher.ds.MySqlDataSource;
import gradle_jdbc_teacher.dto.Department;
import gradle_jdbc_teacher.dto.Employee;
import gradle_jdbc_teacher.dto.Title;
import gradle_jdbc_teacher.util.LogUtil;

public class EmployeeImpl implements EmployeeDao {
	private static final EmployeeImpl instance = new EmployeeImpl();

	public static EmployeeImpl getInstance() {
		return instance;
	}

	private EmployeeImpl() { 
	}

	@Override
	public Employee selectEmployeeByNo(Employee emp) {
		String sql = "select emp_no, emp_name, title, manager, salary, dept, pass, hire_date, pic from employee where emp_no=?";
		try (Connection con = MySqlDataSource.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, emp.getEmpNo());
			LogUtil.prnLog(pstmt);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return getEmployee(rs, true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Employee getEmployee(ResultSet rs,boolean isPic) throws SQLException {
		int empNo = rs.getInt("emp_no");
		String empName = rs.getString("emp_name");
		Title title = new Title(rs.getInt("title"));
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department(rs.getInt("dept"));
		String pass = rs.getString("pass");
		Date hireDate = rs.getTimestamp("hire_date");// rs.getDate()로 작성시 시간표시가 00:00:00으로 세팅됨.
		Employee emp = new Employee(empNo, empName, title, manager, salary, dept, pass, hireDate);
		if (isPic) {
			byte[] pic = rs.getBytes("pic");
			emp.setPic(pic);
		}
		LogUtil.prnLog(emp);
		return emp;
	}

	@Override
	public List<Employee> selectEmployeeByAll() {
		String sql = "select emp_no, emp_name, title, manager, salary, dept, pass, hire_date from employee";
		List<Employee> list = null;
		try (Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LogUtil.prnLog(pstmt);
			if (rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getEmployee(rs, false));
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
 
	@Override
	public int insertEmployee(Employee emp) {
		String sql = "insert into employee(emp_no, emp_name, title, manager, salary, dept, pass, hire_date, pic) values(?, ?, ?, ?, ?, ?, password(?), ?,  ?)";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setInt(3, emp.getTitle().getTitleNo());
			pstmt.setInt(4, emp.getManager().getEmpNo());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDept().getDeptNo());
			pstmt.setString(7, emp.getPass());
//			util.Date->sql.Date로 변환
			pstmt.setTimestamp(8, new Timestamp(emp.getHireDate().getTime()));
			LogUtil.prnLog(pstmt);
			if (emp.getPic()!=null) {
				pstmt.setBytes(9, emp.getPic());
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateEmployee(Employee emp) {
		StringBuilder sql = new StringBuilder("update employee set ");
		if (emp.getEmpName()!=null) sql.append("emp_name=?, ");
		if (emp.getTitle()!=null) sql.append("title=?, ");
		if (emp.getManager()!=null)sql.append("manager=?, ");
		if (emp.getSalary()!=0) sql.append("salary=?, ");
		if (emp.getDept()!=null) sql.append("dept=?, ");
		if (emp.getPass()!=null) sql.append("pass=password(?), ");
		if (emp.getHireDate()!=null) sql.append("hire_date=?, ");
		sql.replace(sql.lastIndexOf(","), sql.length(), " ");
		sql.append("where emp_no=?");
		
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString())){
			int argCnt = 1;
			if (emp.getEmpName()!=null) pstmt.setString(argCnt++, emp.getEmpName());
			if (emp.getTitle()!=null) pstmt.setInt(argCnt++, emp.getTitle().getTitleNo());
			if (emp.getManager()!=null)pstmt.setInt(argCnt++, emp.getManager().getEmpNo());
			if (emp.getSalary()!=0) pstmt.setInt(argCnt++, emp.getSalary());
			if (emp.getDept()!=null)pstmt.setInt(argCnt++, emp.getDept().getDeptNo());
			if (emp.getPass()!=null)pstmt.setString(argCnt++, emp.getPass());
			if (emp.getHireDate()!=null) pstmt.setTimestamp(argCnt++, new Timestamp(emp.getHireDate().getTime()));
			pstmt.setInt(argCnt++, emp.getEmpNo());
			LogUtil.prnLog(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteEmployee(Employee emp) {
		String sql = "delete from employee where emp_no=?";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, emp.getEmpNo());
			LogUtil.prnLog(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Employee isEqualPassword(Employee emp) {
		String sql = "select emp_no, emp_name, title, manager, salary, dept, pass, hire_date from employee where emp_no=? and pass = password(?)";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, emp.getEmpNo());

			pstmt.setString(2, emp.getPass());
			LogUtil.prnLog(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					return getEmployee(rs, false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
