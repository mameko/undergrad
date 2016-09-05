package com.yourcompany.struts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	Connection conn=null;
	
	public Connection makeConnection(){
	try {
		Class.forName("com.mysql.jdbc.Driver");
	
		String url = "jdbc:mysql://127.0.0.1:3306/webchat";
		String user = "root";
		String pw = "123";

		conn = DriverManager.getConnection(url,user,pw);
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return conn;
	}
}
