package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.DBOperationAdapter;
import entity.Student;

/*
* WebService测试类 */
public class TestService {
	// 返回学生列表信息
	public Student[] queryStudents() {
		DBOperationAdapter dbtools = DBOperationAdapter.getInstance();
		Student st = null;
		List<Student> resultList = new ArrayList();
		try {
			ResultSet rs = dbtools.executeQuery("select * from student");
			while (rs.next()) {
				st = new Student();
				st.setId(rs.getInt(1));// 设置学生ID
				st.setNumber(rs.getInt(2));// 设置学生学号
				st.setName(rs.getString(3));// 设置学生姓名
				st.setTel(rs.getString(4));// 设置学生电话
				st.setAddress(rs.getString(5));// 设置学生住址
				resultList.add(st);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList.toArray(new Student[0]);
	}

	// 测试欢迎方法
	public String getGreeting(String name) {
		return "你好" + name;
	}
}
