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

import com.yourcompany.struts.dao.GetURoomDao;
import com.yourcompany.struts.form.RoomForm;

public class GetURoomAction extends DispatchAction {
	int rid;//记录所选房间的房间号
	public ActionForward getURoom(ActionMapping mapping, ActionForm form,// 显示高级用户房间
			HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RoomForm> rfl = new ArrayList<RoomForm>();
		GetURoomDao gurd = new GetURoomDao();
//System.out.println("in get u room");		
		rfl = gurd.getURoom();
		if (rfl != null) {
			request.setAttribute("rfl", rfl);
			return mapping.findForward("success1");
		}
		return mapping.findForward("fail");
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,// 检查是否需要密码
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		rid=Integer.parseInt(request.getParameter("r_id"));
		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		GetURoomDao gurd = new GetURoomDao();
		boolean flag = gurd.check(rid, mid);
		if(flag){
			meg = "ok";
		}else{
			meg = "fail";
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
	
	public ActionForward checkpw(ActionMapping mapping, ActionForm form,// 检查密码的正确性
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String pw = (String)request.getParameter("password");
		GetURoomDao gurd = new GetURoomDao();
		boolean flag =gurd.checkpassword(pw, rid);
		String meg = "";
		if(flag){
			meg = "ok";//密码正确
		}else{
			meg = "密码错误";
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
}
