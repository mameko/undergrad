package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.ChatroomDao;
import com.yourcompany.struts.form.ChatMessage;
import com.yourcompany.struts.form.Userlist;
import com.yourcompany.struts.util.ChatService;

public class ChatroomAction extends DispatchAction {
	public ActionForward getMessage(ActionMapping mapping, ActionForm form,// 从服务器把聊天信息取回
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		if (request.getSession().getAttribute("rid") != null) {
			int r_id = Integer.parseInt(request.getSession()
					.getAttribute("rid").toString());
			int m_id = Integer.parseInt(request.getSession().getAttribute(
					"m_id").toString());

			ChatService cs = new ChatService();
			ArrayList<ChatMessage> cml = new ArrayList<ChatMessage>();
			cml = cs.getMessageInList(r_id, m_id);

			if (cml != null) {
				for (int i = 0; i < cml.size(); i++) {
					ChatMessage cm = new ChatMessage();
					cm = cml.get(i);
					if (cm != null) {
						meg = meg + cm.getName() + " " + cm.getTime() + "<br>"
								+ cm.getMessage() + "<br><br>";
					} else
						meg = "" + meg;
				}
			}else
				meg = "房间已被删除";
		} else
			meg = "已经退出房间";

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

	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,// 按send按钮发送信息
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String megFromClient = request.getParameter("meg");
		int mid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		int rid = Integer.parseInt(request.getSession().getAttribute("rid")
				.toString());
		String un = (String) request.getSession().getAttribute("username");
		DateFormat f = new SimpleDateFormat("hh:mm:ss");
		String time = f.format(new Date());
		ChatService cs = new ChatService();
		ChatMessage cm = new ChatMessage();

		cm.setMessage(megFromClient);
		cm.setName(un);
		cm.setUid(mid);
		cm.setRid(rid);
		cm.setTime(time.toString());

		cs.writeMessageInList(cm);
		return null;
	}

	public ActionForward getUserlist(ActionMapping mapping, ActionForm form,// 从服务器把用户列表取回
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		if (request.getSession().getAttribute("rid") != null) {
			int r_id = Integer.parseInt(request.getSession()
					.getAttribute("rid").toString());

			List<Integer> userlist = new ArrayList<Integer>();
			List<Userlist> ulwithname = new ArrayList<Userlist>();
			ChatService cs = new ChatService();
			userlist = cs.getUserlist(r_id);// 把userlist取到，不过只有id
			ChatroomDao cd = new ChatroomDao();// 所以要用dao把name取来
			ulwithname = cd.addUname(userlist);

			for (int i = 0; i < ulwithname.size(); i++) {
				meg = meg + "<a href = '#' onmouseover = 'onpop(event,"+ulwithname.get(i).getM_id()+")' onmouseout = 'stoppop()' onclick = 'twopeoplechat("+ulwithname.get(i).getM_id()+")'>"+ulwithname.get(i).getName() + "("
						+ ulwithname.get(i).getM_id() + ")"+"</a>"+"<br>";
			}
		} else
			meg = "";
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
