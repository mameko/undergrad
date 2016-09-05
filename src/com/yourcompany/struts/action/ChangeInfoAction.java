package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.ChangeInfoDao;
import com.yourcompany.struts.form.RegisterForm;

public class ChangeInfoAction extends DispatchAction {
	public ActionForward changeInfo(ActionMapping mapping, ActionForm form,// 显示房间信息
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String un = request.getParameter("username_c");
		String pw = request.getParameter("password_c");

		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		// System.out.println(mid);

		if (un.equals("") || pw.equals("")) {// 检查密码和用户名是否为空
			meg = "用户名或密码不能为空";
		} else if (un.length() > 20) {
			meg = "用户名超长";// 用户名超长�û���
		} else if (pw.length() > 10) {
			meg = "密码超长";// 密码超长
		} else {
			String ds = request.getParameter("description_c");// 注册
			if (ds.length() > 500) {
				meg = "个人描述超长";
			} else {
				ChangeInfoDao cid = new ChangeInfoDao();
				RegisterForm rf = new RegisterForm();
				rf.setM_descripton(ds);
				rf.setM_name(un);
				rf.setM_password(pw);
				if(cid.change(rf, mid)){
					meg = "更新成功";
				}else
					meg = "更新失败";
				
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
}
