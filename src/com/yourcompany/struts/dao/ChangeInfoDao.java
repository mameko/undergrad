package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yourcompany.struts.form.RegisterForm;
import com.yourcompany.struts.util.DBConnection;

public class ChangeInfoDao {
	private Connection conn = new DBConnection().makeConnection();
	private ResultSet rs = null;
	
	public boolean change(RegisterForm rf,int m_id){
		String sql = "update member set m_description=?,m_password=?,m_name=? where m_id = ? ";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rf.getM_descripton());
			ps.setString(2, rf.getM_password());
			ps.setString(3, rf.getM_name());
			ps.setInt(4, m_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
