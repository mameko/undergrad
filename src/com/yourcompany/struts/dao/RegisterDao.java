package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yourcompany.struts.form.RegisterForm;
import com.yourcompany.struts.util.DBConnection;

public class RegisterDao {
	private Connection conn = new DBConnection().makeConnection();
	private ResultSet rs = null;

	public int check(String name, String password) {
		PreparedStatement ps;
		if (name.length() > 20) {
			return 2;// 用户名超长�û���
		} else if (password.length() > 10) {
			return 3;// 密码超长
		} else {
				try {
					String sql = "select * from member where m_name=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, name);
					rs = ps.executeQuery();
					if (rs.next()) {
						return 1;// 用户名已被使用 ��
					}				
				// 注册人数达到500人时，自动删除注册很久但在线时间不长的用户。在最早注册的前400名用户中选择在线时间最小的用户删除。
				String sql1 = "select count(*) from member";
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {//用户总人数
					int num = rs1.getInt(1);
					if (num <= 3) {// 用户人数在500人或以下
						return 4;// 用户名不存在可以注册
					}else//删除用户
					{//把前四百名用户取出，然后取出onlinetime的最小值
						String sql2 = "select min(onlinetime) from (select * from member limit 400,1) as m";
						PreparedStatement ps2 = conn.prepareStatement(sql2);
						ResultSet rs2 = ps2.executeQuery();
						if(rs2.next()){//根据最小值删除用户，可能删除不止一个用户
							int min = rs2.getInt(1);
							String sql3 = "delete from member where onlinetime = ? and not m_id = ?";
							PreparedStatement ps3 = conn.prepareStatement(sql3);
							ps3.setInt(1, min);
							ps3.setInt(2, 1);
							ps3.executeUpdate();
							return 4;
						}						
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;// 系统出错��
			}	
		}
		return 0;
}

	public boolean add(RegisterForm rf) {

		String sql = "insert into member(m_description,m_password,m_name) values(?,?,?) ";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, rf.getM_descripton());
			ps.setString(2, rf.getM_password());
			ps.setString(3, rf.getM_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}