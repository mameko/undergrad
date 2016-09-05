package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.RegisterDao;
import com.yourcompany.struts.form.RegisterForm;

public class RegisterAction extends DispatchAction {
	public ActionForward regist(ActionMapping mapping, ActionForm form,//
		HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=utf-8");
		RegisterDao rd = new RegisterDao();// 检查需要注册的信息的合法性
		String meg = "";
		String un = request.getParameter("username_r");
		String pw = request.getParameter("password_r");// 没输入时传来的是空字符串而不是null
		if (un.equals("") || pw.equals("")) {// 检查密码和用户名是否为空
			meg = "用户名或密码不能为空";
		} else {
			int checkresult = rd.check(un, pw);// 检查用户名的合法性
			switch (checkresult) {
			case 0:
				meg = "系统出错";
				break;
			case 1:
				meg = "用户名已被使用";
				break;
			case 2:
				meg = "用户名超长";
				break;
			case 3:
				meg = "密码超长";
				break;
			case 4:
				String ds = request.getParameter("description_r");//注册
				if (ds.length() > 500) {
					meg = "个人描述超长";
				} else {
					RegisterForm rform = (RegisterForm) form;
					rform.setM_name(un);
					rform.setM_descripton(ds);
					rform.setM_password(pw);
					boolean flag = rd.add(rform);
					
					if (flag) {
						meg = "注册成功";
					} else {
						meg = "注册失败";
					}
				}
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
