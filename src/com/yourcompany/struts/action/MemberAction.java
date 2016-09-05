package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.MemberDao;

public class MemberAction extends DispatchAction {
	public ActionForward getUserDes(ActionMapping mapping, ActionForm form,// 鼠标定在用户列中的用户名时，显示用户个人描述
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int m_id = Integer.parseInt(request.getParameter("m_id"));
		MemberDao md = new MemberDao();
		meg = md.getDes(m_id);
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
