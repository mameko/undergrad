package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.RoomDao;
import com.yourcompany.struts.form.RoomForm;
import com.yourcompany.struts.util.ChatService;

public class RoomAction extends DispatchAction {
	public ActionForward add(ActionMapping mapping, ActionForm form,// 增加房间
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";

		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		String rn = request.getParameter("roomname_r");
		String pw = request.getParameter("password_r");
		String ds = request.getParameter("description_r");
		String rv = request.getParameter("volumn_r");

		if (rn.equals("")) {// 检查密码和房间名是否为空
			meg = "房间名不能为空";
		} else {
			if (rn.length() > 20) {
				meg = "用户名超长";// 房间名超长�û���
			} else if (pw.length() > 10) {
				meg = "密码超长";// 密码超长
			} else if (Integer.parseInt(rv) > 30) {
				meg = "用户房间容量只允许30个人";
			} else if (ds.length() > 500) {
				meg = "房间描述超长";
			} else {
				RoomForm rf = new RoomForm();
				RoomDao rd = new RoomDao();

				rf.setM_id(mid);
				rf.setR_description(ds);
				rf.setR_name(rn);
				rf.setR_password(pw);
				rf.setVolumn(rv);

				boolean flag = rd.add(rf);// 添加房间
				if (flag) {
					meg = "添加成功";					
				} else
					meg = "添加失败";
			}
		}
		try {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			out.println(meg);

			System.out.println(meg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,// 检查开房用户的合法性
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		RoomDao rd = new RoomDao();
		// System.out.println("in check");
		int checkresult = rd.check(mid);
		switch (checkresult) {
		case 1:
			meg = "你不是高级用户，通过在线时间的增加可以升级";
			break;
		case 2:
			meg = "已经开了3间房啦，不能再开啦";
			break;
		case 3:
			meg = "ok";
		}
		try {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			out.println(meg);

			System.out.println(meg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,// 删除房间
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int rid = Integer.parseInt(request.getParameter("r_id"));
		RoomDao rd = new RoomDao();
		boolean flag = rd.del(rid);
		if(flag){
			meg = "删除成功";
		}else
			meg = "删除失败";
			
		try {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			out.println(meg);

			System.out.println(meg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public ActionForward check2(ActionMapping mapping, ActionForm form,// 检查删除房间用户的合法性
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		RoomDao rd = new RoomDao();
		// System.out.println("in check");
		int checkresult = rd.check2(mid);
		switch (checkresult) {
		case 1:
			meg = "你不是高级用户，通过在线时间的增加可以升级";
			break;
		case 2:
			meg = "ok";
			break;
		case 3:
			meg = "还没开过房间不能删除";
		}
		try {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			out.println(meg);

			System.out.println(meg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward getRoom(ActionMapping mapping, ActionForm form,// 把可删除的房间列出来
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		ArrayList<RoomForm> rfl = new ArrayList<RoomForm>();
		RoomDao rd = new RoomDao();
		rfl= rd.get(mid);
		if(rfl!=null){
			for(int i = 0;i<rfl.size();i++){
				RoomForm rf = new RoomForm();
				rf = rfl.get(i);
				String rid = String.valueOf(rf.getR_id());
				meg = "<a href ='#' id = "+rid+" onClick = ondel("+rid+")>"+rf.getR_name()+"</a>"+"<br>"+ meg;
			}
			
		}else 
			meg = "系统出错";
				
		try {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			out.println(meg);

			System.out.println(meg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}