package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.form.ChatMessage;
import com.yourcompany.struts.util.ChatService;
import com.yourcompany.struts.util.PrivateChatService;

public class PrivateChatAction extends DispatchAction {
	public ActionForward getFlag(ActionMapping mapping, ActionForm form,// 从请求列表（questlist）中得到该用户是否被请求
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String bid = request.getSession().getAttribute("m_id").toString();// b为被请求者
		PrivateChatService pc = new PrivateChatService();
		int flag = pc.getFlag(bid);
		if (flag == 0) {// 没人叫
		} else {
			meg = String.valueOf(flag) + " 号用户请求与你单独聊天";
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

	public ActionForward setChatQuest(ActionMapping mapping, ActionForm form,// 设置请求列表
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int bid = Integer.parseInt(request.getParameter("b_id"));// b为被请求者
		int aid = Integer.parseInt(request.getSession().getAttribute("m_id")// a为请求者
				.toString());
		PrivateChatService pc = new PrivateChatService();
		int c = pc.setQuestList(aid, bid);
		switch (c) {
		case 0:
			meg = "请求提交成功，等待对方回应";
			break;
		case 1:
			meg = "已在请求中，请稍候";
			break;
		case 2:
			meg = "聊天中";
			break;
		case 3:
			meg = "暂时无足够资源支持你的私聊，请稍候再请求";
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

	public ActionForward setFlag(ActionMapping mapping, ActionForm form,// 设置列表
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String answer = request.getParameter("answer").toString();
		String bid = request.getSession().getAttribute("m_id").toString();// b为被请求者

		PrivateChatService pc = new PrivateChatService();
		int k = pc.setFlag(bid, answer);
		if (k == 1) {
			meg = "ok";// 设置成功

			try {
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("utf-8");
				out.println(meg);

				System.out.println(meg);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public ActionForward getAnswer(ActionMapping mapping, ActionForm form,// 获得b用户的回复
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String aid = request.getSession().getAttribute("m_id").toString();// a为请求者

		PrivateChatService pc = new PrivateChatService();
		int flag = pc.getAns(aid);

		if (flag == 1) {// 同意聊天
			meg = "accept";
		} else if (flag == 2) {// b君还未有反应时，a君又来问啦
			meg = "2";
		} else
			meg = "对方用户不同意与你聊天";

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

	public ActionForward pcPrepare(ActionMapping mapping, ActionForm form,// 获得b用户的回复
			HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession().getAttribute("rid") != null) {// 退出原有房间
			int mid = Integer.parseInt(request.getSession()
					.getAttribute("m_id").toString());
			int rid = Integer.parseInt(request.getSession().getAttribute("rid")
					.toString());
			request.getSession().removeAttribute("rid");
			ChatService cs = new ChatService();
			cs.quitRoom(rid, mid);
		}
		// return mapping.findForward("jumpPc");
		return null;
	}

	public ActionForward getMessage(ActionMapping mapping, ActionForm form,// 取messagepool中的记录
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String abid = "";
		ArrayList<ChatMessage> cml = new ArrayList<ChatMessage>();
		int aorbid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());// 自己的id
		// 先查对方id
		PrivateChatService pc = new PrivateChatService();
		int oid = pc.getOtherId(aorbid);
		if (oid != 0) {// 找到对方的id
			if (aorbid > oid) {
				abid = String.valueOf(oid) + "_" + String.valueOf(aorbid);
			} else
				abid = String.valueOf(aorbid) + "_" + String.valueOf(oid);
			cml = pc.getMessageInList(abid);
			if (cml != null) {// 从返回的messagelist中
				for (int i = 0; i < cml.size(); i++) {// 把聊天信息组装好，发给用户
					ChatMessage cm = new ChatMessage();
					cm = cml.get(i);
					if (cm != null) {
						meg = meg + cm.getName() + " " + cm.getTime() + " "
								+ cm.getMessage() + "\n\n";
					} else
						meg = "" + meg;
				}
			} else
				meg = "对方已退出私聊";
		} else
			meg = "对方已退出私聊";

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
		int aorbid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		String un = (String) request.getSession().getAttribute("username");

		DateFormat f = new SimpleDateFormat("hh:mm:ss");
		String time = f.format(new Date());
		PrivateChatService pc = new PrivateChatService();
		ChatMessage cm = new ChatMessage();
		String abid = "";
		int oid = pc.getOtherId(aorbid);

		cm.setMessage(megFromClient);
		cm.setName(un);
		cm.setUid(aorbid);
		cm.setTime(time.toString());

		if (oid != 0) {// 生成messagepool的key，abid
			if (aorbid > oid) {
				abid = String.valueOf(oid) + "_" + String.valueOf(aorbid);
			} else
				abid = String.valueOf(aorbid) + "_" + String.valueOf(oid);
			pc.writeMessageInList(abid, cm);
		}
		return null;
	}

	public ActionForward quit(ActionMapping mapping, ActionForm form,// 退出私聊时回收资源
			HttpServletRequest request, HttpServletResponse response) {
		int aorbid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		PrivateChatService pc = new PrivateChatService();
		String abid = "";
		int oid = pc.getOtherId(aorbid);

		if (oid != 0) {// 生成messagepool的key，abid
			if (aorbid > oid) {
				abid = String.valueOf(oid) + "_" + String.valueOf(aorbid);
			} else
				abid = String.valueOf(aorbid) + "_" + String.valueOf(oid);
			pc.quitPc(abid);
		}
		return mapping.findForward("ok");
	}
}
