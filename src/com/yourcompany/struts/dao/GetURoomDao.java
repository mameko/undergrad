package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.yourcompany.struts.form.RoomForm;
import com.yourcompany.struts.util.ChatService;
import com.yourcompany.struts.util.DBConnection;

public class GetURoomDao {
	private Connection conn = new DBConnection().makeConnection();

	public ArrayList<RoomForm> getURoom() {// 把房间信息返回
		PreparedStatement ps;
		ResultSet rs = null;
		ArrayList<RoomForm> rfl = new ArrayList<RoomForm>();
		String sql = "select * from chatroom where not m_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while (rs.next()) {
				RoomForm rf = new RoomForm();
				rf.setM_id(rs.getInt("m_id"));
				rf.setR_description(rs.getString("r_description"));
				rf.setR_id(rs.getInt("r_id"));
				rf.setR_name(rs.getString("r_name"));
				rf.setR_password(rs.getString("r_password"));
				rf.setVolumn(rs.getString("volume"));
				rfl.add(rf);
				
				//把userRoomMap中的对应关系补上
				ChatService cs = new ChatService();
				cs.initUserRoomMap(rs.getInt("r_id"));
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rfl;
	}

	public boolean check(int rid, int mid) {// 检查是否需要password
		PreparedStatement ps;
		ResultSet rs = null;
		String sql = "select * from chatroom where r_id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				String pw = rs.getString("r_password");
				int m_id = rs.getInt("m_id");
				if (pw.equals("") || mid == m_id) {
					return true;
				} else
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkpassword(String pw, int rid) {// 检查密码的正确性的dao
		String sql = "select r_password from chatroom where r_id=?";
		PreparedStatement ps;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(pw)) {
					return true;//密码正确
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
