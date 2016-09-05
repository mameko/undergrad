package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yourcompany.struts.util.DBConnection;

public class LoginDao {
	private Connection conn = new DBConnection().makeConnection();
	private ResultSet rs = null;

	public int check(String name,String password) {
		PreparedStatement ps;
			try {
					if(conn==null){System.out.println("conn 创建失败");}
					String sql = "select * from member where m_name=? && m_password = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, name);
					ps.setString(2, password);
					rs=ps.executeQuery();
					if (rs.next()) {						
						return rs.getInt("m_id");//登录成功
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;//系统出错
					}
				return 1;//用户不存在��
			
	}

}
