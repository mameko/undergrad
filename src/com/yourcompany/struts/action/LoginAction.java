package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.LoginDao;
import com.yourcompany.struts.dao.RegisterDao;
import com.yourcompany.struts.form.ChatMessage;
import com.yourcompany.struts.form.RegisterForm;
import com.yourcompany.struts.util.ChatService;

public class LoginAction extends DispatchAction {
	public ActionForward check(ActionMapping mapping, ActionForm form,// ����û���¼
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=utf-8");

		LoginDao rd = new LoginDao();// 检查用户名和密码的合法性
		String meg = "";
		String un = request.getParameter("username");
		String pw = request.getParameter("password");// 没输入时传来的是空字符串而不是null
		DateFormat f = new SimpleDateFormat("hh");
		String time = f.format(new Date());
		if (un.equals("") || pw.equals("")) {// 检查密码和用户名是否为空
			meg = "用户名或密码不能为空";
		} else {
			if (un.length() > 20) {
				meg = "用户名超长";// 用户名超长�û���
			} else if (pw.length() > 10) {
				meg = "密码超长";// 密码超长
			} else {
				int checkresult = rd.check(un, pw);// 检查用户名的合法性
				switch (checkresult) {
				case 0:
					meg = "系统出错";
					break;
				case 1:
					meg = "用户不存在";
					break;
				default:
					meg = "登录成功";
					request.getSession().setAttribute("m_id", checkresult);// 设置好用户
					request.getSession().setAttribute("username", un);
					request.getSession().setAttribute("time", Integer.parseInt(time.toString()));
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
