package com.yourcompany.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yourcompany.struts.form.Userlist;
import com.yourcompany.struts.util.DBConnection;

public class ChatroomDao {
	private Connection conn = new DBConnection().makeConnection();

	public List<Userlist> addUname(List<Integer> userlist) {
		ArrayList<Userlist> ulwithname = new ArrayList<Userlist>();

		for (int i = 0; i < userlist.size(); i++) {
			String sql = "select m_name from member where m_id=?";
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userlist.get(i));
				rs = ps.executeQuery();
				Userlist ul = new Userlist();
				if(rs.next()){
					ul.setM_id(userlist.get(i));
					ul.setName(rs.getString("m_name"));
				}
				ulwithname.add(ul);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ulwithname;
	}
}
