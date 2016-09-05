package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yourcompany.struts.util.DBConnection;

public class MemberDao {
	private Connection conn = new DBConnection().makeConnection();
	public String getDes(int m_id){
		String des ="";
		try {
			if(conn==null){System.out.println("conn 创建失败");}
			String sql = "select * from member where m_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, m_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				des = rs.getString("m_description");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return des;
	}
}
