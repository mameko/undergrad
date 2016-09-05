package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.yourcompany.struts.form.RoomForm;
import com.yourcompany.struts.util.ChatService;
import com.yourcompany.struts.util.DBConnection;

public class RoomDao {
	private Connection conn = new DBConnection().makeConnection();
	private ResultSet rs = null;
	private ResultSet rs3 = null;
	private ResultSet rs4 = null;

	public int check(int m_id) {// 检查增加时合法性
		PreparedStatement ps;
		try {
			String sql = "select max(onlinetime) from member";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String sql4 = "select m_id from member where onlinetime = ?";
				PreparedStatement ps4 = conn.prepareStatement(sql4);
				ps4.setInt(1, rs.getInt(1));
				rs4 = ps4.executeQuery();
				if (rs4.next()) {
					if (m_id == rs4.getInt(1)) {
						String sql2 = "select count(*) from chatroom where m_id = ?";
						PreparedStatement ps2 = conn.prepareStatement(sql2);
						ps2.setInt(1, m_id);

						ResultSet rs2 = ps2.executeQuery();
						if (rs2.next()) {// 找到该用户开了多少间房间
							if (rs2.getInt(1) > 3) {
								return 2;// 已经开了3间房啦
							} else
								return 3;// 还没开过3间房间，可以继续开。
						}
					} else {
						String sql6 = "select * from chatroom where m_id = ?";
						PreparedStatement ps6 = conn.prepareStatement(sql6);
						ps6.setInt(1, m_id);
						ResultSet rs6 = ps6.executeQuery();
						while (rs6.next()) {// 删除房间前把房间的相关资源回收
							ChatService cs = new ChatService();
							cs.resetUserRoomMap(rs6.getInt("r_id"));

							String sql5 = "delete from chatroom where m_id = ?";// 删除房间
							PreparedStatement ps5 = conn.prepareStatement(sql5);
							ps5.setInt(1, m_id);
							ps5.executeUpdate();
						}
						return 1;// 不是高级用户。
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;// 系统出错��
		}
		return 0;
	}

	public boolean add(RoomForm rf) {// 增加房间
		String sql = "insert into chatroom(r_description,r_password,volume,r_name,m_id) values(?,?,?,?,?) ";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rf.getR_description());
			ps.setString(2, rf.getR_password());
			ps.setString(3, rf.getVolumn());
			ps.setString(4, rf.getR_name());
			ps.setInt(5, rf.getM_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		String sql2 = "select max(r_id) from chatroom";// 把新开房间的r_id与URoomMessage的对应关系放到userRoomMap
		PreparedStatement ps2;
		ResultSet rs5 = null;
		int r_id = 0;
		try {
			ps2 = conn.prepareStatement(sql2);
			rs5 = ps2.executeQuery();
			if (rs5.next()) {
				r_id = rs5.getInt(1);// 得到房号
				ChatService cs = new ChatService();
				cs.setUserRoomMap(r_id);
			} else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public int check2(int m_id) {// 检查删除时合法性
		PreparedStatement ps;
		try {
			String sql = "select max(onlinetime) from member";// 查找在线时间最多的用户
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String sql4 = "select m_id from member where onlinetime = ?";// 把在线时间最多的用户的帐号查出来
				PreparedStatement ps4 = conn.prepareStatement(sql4);
				ps4.setInt(1, rs.getInt(1));
				rs4 = ps4.executeQuery();
				if (rs4.next()) {
					if (m_id == rs4.getInt(1)) {
						String sql2 = "select count(*) from chatroom where m_id = ?";// 数一下他开了多少房间
						PreparedStatement ps2 = conn.prepareStatement(sql2);
						ps2.setInt(1, m_id);

						ResultSet rs2 = ps2.executeQuery();
						if (rs2.next()) {// 找到该用户开了多少间房间
							if (rs2.getInt(1) > 0) {
								return 2;// 已经开过房了可以删除
							} else
								return 3;// 还没开过房间不能删除。
						}
					} else {
						String sql6 = "select * from chatroom where m_id = ?";
						PreparedStatement ps6 = conn.prepareStatement(sql6);
						ps6.setInt(1, m_id);
						ResultSet rs6 = ps6.executeQuery();
						while (rs6.next()) {// 删除房间前把房间的相关资源回收
							ChatService cs = new ChatService();
							cs.resetUserRoomMap(rs6.getInt("r_id"));

							String sql5 = "delete from chatroom where m_id = ?";
							PreparedStatement ps5 = conn.prepareStatement(sql5);
							ps5.setInt(1, m_id);
							ps5.executeUpdate();
						}
						return 1;// 不是高级用户。
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;// 系统出错��
		}
		return 0;
	}

	public ArrayList<RoomForm> get(int mid) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<RoomForm> rfl = new ArrayList<RoomForm>();
		try {
			String sql = "select * from chatroom where m_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mid);
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return rfl;
	}

	public boolean del(int rid) {// 删除
		PreparedStatement ps;
		String sql = "delete from chatroom where r_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rid);
			ps.executeUpdate();

			ChatService cs = new ChatService();
			cs.resetUserRoomMap(rid);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
