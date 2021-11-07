import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DAO {
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public DAO() // 생성자
	{
		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?serverTimezone=UTC", "root", "1213");

		} catch (SQLException e) {
			System.out.println(e + "=> 연결 fail");
		}
	}

	public void dbClose() { /// DB 닫기
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}

	public int userListInsert(InsertDialog user) // Insert 용 메소드( InsertDialog에서 사용할 예정)
	{
		int result = 0;
		try {
			ps = con.prepareStatement("insert into EMPLOYEE values (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, user.Fname.getText());
			ps.setString(2, user.Minit.getText());
			ps.setString(3, user.Lname.getText());
			ps.setString(4, user.Ssn.getText());
			ps.setString(5, user.Bdate.getText());
			ps.setString(6, user.Address.getText());
			ps.setString(7, user.Sex.getSelectedItem().toString());
			ps.setString(8, user.Salary.getText());
			ps.setString(9, user.Super_ssn.getText());
			ps.setString(10, user.Dno.getText());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> userListInsert fail");
		} finally {
			dbClose();
		}

		return result;

	}

	public void userSelectAll(DefaultTableModel t_model) // 모든 attribute 출력
	{
		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
							+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn;");

			for (int i = 0; i < t_model.getRowCount();) // 기존 tuple 지우기
			{
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), Boolean.FALSE };
				t_model.addRow(data); // DefaultTableModel에 tuple 추가
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelectAll fail");
		} finally {
			dbClose();
		}
	}

	// 검색 범위
	public void userSelect(DefaultTableModel t_model, String sel, String Text) {
		try {
			st = con.createStatement();
			if (sel == "부서") {
				rs = st.executeQuery(
						"SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
								+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn WHERE d.DNAME = '"
								+ Text + "'");
			} else if (sel == "성별") {
				rs = st.executeQuery(
						"SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
								+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn WHERE e1.SEX = '"
								+ Text + "'");
			} else if (sel == "연봉") {
				rs = st.executeQuery(
						"SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
								+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn WHERE e1.SALARY > '"
								+ Text + "'");
			} else if (sel == "생일") {
				rs = st.executeQuery(
						"SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
								+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn WHERE DATE_FORMAT(e1.BDATE, '%m') = '"
								+ Text + "'");
			} else if (sel == "부하직원") {
				rs = st.executeQuery(
						"SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
								+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn WHERE SUPERVISOR = '"
								+ Text + "'");
			}
			
			
			for (int i = 0; i < t_model.getRowCount();) // 기존 tuple 지우기
			{
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), Boolean.FALSE };
				t_model.addRow(data); // DefaultTableModel에 tuple 추가
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelect fail");
		} finally {
			dbClose();
		}
	}

	
	
	public int userDelete(String Name) // 직원정보 삭제
	{
		int result = 0;
		try {
			String query = "delete from employee where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " 직원정보 삭제됨");
		} catch (SQLException e) {
			System.out.println(e + "=> userDelete fail");
		} finally {
			dbClose();
		}

		return result;
	}

	public int userUpdate_add(String Name, String Text) // 직원정보 갱신 - 원래 주소, 성별, 급여도 ?로 입력받아서 구하는 하나의 함수로 만들었으나, 이럴경우
														// 'address' 형식으로 입력되서 에러발생 -> 어쩔수없이 세개로 나눔(데이터 갱신1.png 참조)
	{
		int result = 0;
		try {
			String query = "update employee set address = ? where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, Text);
			ps.setString(2, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " 주소 변경됨");
		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

	public int userUpdate_sex(String Name, String Text) {
		int result = 0;
		try {
			String query = "update employee set sex = ? where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, Text);
			ps.setString(2, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " 성별 변경됨");
		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

	public int userUpdate_sal(String Name, String Text) {
		int result = 0;
		try {
			String query = "update employee set salary = ? where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, Text);
			ps.setString(2, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " 급여 변경됨");
		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

}
