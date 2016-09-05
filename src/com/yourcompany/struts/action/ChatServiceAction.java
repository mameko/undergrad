package com.yourcompany.struts.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.form.ChatMessage;
import com.yourcompany.struts.util.ChatService;

public class ChatServiceAction extends DispatchAction {
	public ActionForward setMessage(ActionMapping mapping, ActionForm form,// 把用户所发的数据在类ChatMessage中组装
			HttpServletRequest request, HttpServletResponse response) {//好放入message pool

	//	if (request.getSession().getAttribute("m_id") != null) {
			int m_id = Integer.parseInt(request.getSession().getAttribute(
					"m_id").toString());
			String un = (String) request.getSession().getAttribute("username");
			int r_id = Integer.parseInt((String) request.getSession()
					.getAttribute("rid").toString());
			String meg = "";
			DateFormat f = new SimpleDateFormat("hh:mm:ss");
			String time = f.format(new Date());
			if (request.getSession().getAttribute("meg") != null) {//第一次进来时的消息
				meg = (String) request.getSession().getAttribute("meg");
				request.getSession().removeAttribute("meg");
			}
			ChatMessage cm = new ChatMessage();// 把要发送的数据放好在ChatMessage类里面
			cm.setMessage(meg);
			cm.setName(un);
			cm.setUid(m_id);
			cm.setRid(r_id);
			cm.setTime(time.toString());

			ChatService cs = new ChatService();
			cs.writeMessageInList(cm);
	//	}

	//	System.out.println("before go to chatroom");
		return mapping.findForward("success");
		}
}
