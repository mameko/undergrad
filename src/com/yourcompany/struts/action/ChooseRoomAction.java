package com.yourcompany.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.dao.ChooseRoomDao;
import com.yourcompany.struts.form.RoomForm;
import com.yourcompany.struts.util.ChatService;

public class ChooseRoomAction extends DispatchAction {

	public ActionForward showRoomInfo(ActionMapping mapping, ActionForm form,// 显示房间信息
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int r_id = Integer.parseInt(request.getParameter("r_id"));

		ChooseRoomDao crd = new ChooseRoomDao();
		RoomForm rf = new RoomForm();
		rf = crd.fetchRoomInfo(r_id);
		if (rf == null) {
			meg = "房间已被删除，请刷新";
		} else {
			int mid = rf.getM_id();
			String m_id = String.valueOf(mid);
			// 现有人数

			int pnum = getNum(r_id);

			meg = "房间名称：" + rf.getR_name().toString() + "<br>" + "总容量："
					+ rf.getVolumn().toString() + "<br>" + "现有人数：" + pnum
					+ "<br>" + "房间主人帐号：" + m_id + "<br>" + "房间描述："
					+ rf.getR_description().toString();
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

	public ActionForward quit(ActionMapping mapping, ActionForm form,// 用户退出本站
			HttpServletRequest request, HttpServletResponse response) {// 把在线时间算好以后，把time也清掉
	// System.out.println("in quit");
		int m_id = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		request.getSession().removeAttribute("m_id");
		request.getSession().removeAttribute("username");

		int time = Integer.parseInt(request.getSession().getAttribute("time")
				.toString());// 计算在线时间
		DateFormat f = new SimpleDateFormat("hh");
		String t = f.format(new Date());
		int now = Integer.parseInt(t.toString());
		int onlinetime = 0;

		int temp = Math.abs(time - now);// 一次登录最长只能在线5小时
		if (temp > 5) {
			onlinetime = 5;
		} else
			onlinetime = temp;

		ChooseRoomDao cd = new ChooseRoomDao();
		cd.setOlinetime(onlinetime, m_id);

		return null;
	}

	int getNum(int r_id) {// 取得房间人数
		int pnum = 0;
		ChatService cs = new ChatService();
		pnum = cs.getUNumber(r_id);
		return pnum;
	}

	public ActionForward inRoomCheck(ActionMapping mapping, ActionForm form,// 进入房间前，检查该房间是否满员
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		// System.out.println("in inRoomCheck");

		int r_id = Integer.parseInt(request.getParameter("r_id"));
		int pnum = getNum(r_id);

		ChooseRoomDao crd = new ChooseRoomDao();
		RoomForm rf = new RoomForm();
		rf = crd.fetchRoomInfo(r_id);
		int vol = Integer.parseInt(rf.getVolumn());
		if (pnum >= vol) {
			meg = "该房间已经满了，请选择其它房间";
		} else {// 房间还没满，可以进入
			if (r_id > 5) {
				meg = "ok2";// 用户建的房间，下一步检查是否需要密码
			} else
				meg = "ok1";// 公共房间
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

	public ActionForward inRoomPre(ActionMapping mapping, ActionForm form,// 把XXX进入房间信息方到message
			// pool里
			HttpServletRequest request, HttpServletResponse response) {
		ChatService cs = new ChatService();
		int r_id = Integer.parseInt(request.getParameter("r_id"));
		if (request.getSession().getAttribute("m_id") == null) {
			return mapping.findForward("fail");
		}
		int m_id = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		boolean flag = cs.addUserToRoomList(r_id, m_id);
		String meg = "进入了房间";
		request.getSession().setAttribute("meg", meg);
		request.getSession().setAttribute("rid", r_id);// 便于以后按send发信息时获取房间号
		if (flag) {
			return mapping.findForward("success");
		} else
			return mapping.findForward("fail");
	}

	public ActionForward quitRoom(ActionMapping mapping, ActionForm form,// 用户退出房间时把session中的房号清掉,
			HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession().getAttribute("rid") != null) {
			int mid = Integer.parseInt(request.getSession()
					.getAttribute("m_id").toString());
			int rid = Integer.parseInt(request.getSession().getAttribute("rid")
					.toString());
			request.getSession().removeAttribute("rid");
			ChatService cs = new ChatService();
			cs.quitRoom(rid, mid);
		}
		return mapping.findForward("removesuccess");

	}
}
