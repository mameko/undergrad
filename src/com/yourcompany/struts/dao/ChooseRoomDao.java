package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yourcompany.struts.form.RoomForm;
import com.yourcompany.struts.util.DBConnection;

public class ChooseRoomDao {
	private Connection conn = new DBConnection().makeConnection();
	private ResultSet rs = null;

	public RoomForm fetchRoomInfo(int r_id) {
		try {
			if (conn == null) {
				System.out.println("conn 创建失败");
			}
			String sql = "select * from chatroom where r_id=?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, r_id);

			rs = ps.executeQuery();
			if (rs.next()) {
				RoomForm rf = new RoomForm();
				rf.setM_id(rs.getInt("m_id"));
				System.out.println(rs.getInt("m_id"));
				rf.setR_id(rs.getInt("r_id"));
				rf.setR_description(rs.getString("r_description"));
				rf.setR_name(rs.getString("r_name"));
				rf.setR_password(rs.getString("r_password"));
				rf.setVolumn(rs.getString("volume"));
				return rf;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}	
	
	public void setOlinetime(int time,int m_id){
		String sql = "select onlinetime from member where m_id=?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, m_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				int to = Integer.parseInt(rs.getString(1).toString());
				String tn = String.valueOf(time + to);
				
				PreparedStatement ps2;
				String sql2 = "update member set onlinetime=? where m_id = ? ";
				ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, tn);
				ps2.setInt(2, m_id);
				ps2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
