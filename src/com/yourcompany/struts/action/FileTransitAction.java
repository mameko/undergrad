package com.yourcompany.struts.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.yourcompany.struts.util.FileTransitService;
import com.yourcompany.struts.util.PrivateChatService;

public class FileTransitAction extends DispatchAction {
	public ActionForward setFileList(ActionMapping mapping, ActionForm form,// a君上传文件到服务器，b君轮询到有文件给他的时候，他就到服务器里面拿
			HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession().getAttribute("fileN") != null) {
			String filename = request.getSession().getAttribute("fileN")
					.toString();
			int aid = Integer.parseInt(request.getSession()
					.getAttribute("m_id").toString());
			String stoset = "";
			// 查b君的id
			PrivateChatService pc = new PrivateChatService();
			int oid = pc.getOtherId(aid);
			if (oid != 0) {
				stoset = String.valueOf(aid) + "_" + String.valueOf(oid);
			} else
				System.out.println("error happen when upload file");

			FileTransitService fs = new FileTransitService();
			fs.setFileList(stoset, filename);
		}
		return null;
	}

	public ActionForward getFileList(ActionMapping mapping, ActionForm form,// b君轮询是否有人发东西给自己
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		int bid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		// 查a君的id

		String key = "";
		PrivateChatService pc = new PrivateChatService();
		int oid = pc.getOtherId(bid);
		if (oid != 0) {
			key = String.valueOf(oid) + "_" + String.valueOf(bid);
		} else
			System.out.println("error happen when upload file");
		FileTransitService fs = new FileTransitService();
		String status = fs.getFileListCond(key);
		String[] con_sv = status.split("_");
		int flag = 0;
		if (con_sv.length > 1) {
			flag = Integer.parseInt(con_sv[1]);
		}
		if (flag == 1) {
			meg = "yes";
			request.getSession().setAttribute("fn", con_sv[0]);
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

	public ActionForward setFileListStatus(ActionMapping mapping,
			ActionForm form,// b君轮询是否有人发东西给自己
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String status = request.getParameter("status");
		int bid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		String key = "";
		// 查b君的id
		PrivateChatService pc = new PrivateChatService();
		int oid = pc.getOtherId(bid);
		if (oid != 0) {
			key = String.valueOf(oid) + "_" + String.valueOf(bid);
		}
		FileTransitService fs = new FileTransitService();
		fs.setFileListCond(key, status);
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

	public ActionForward getAnswer(ActionMapping mapping, ActionForm form,// a君轮询b君的反应
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String meg = "";
		String key = "";
		String status = "";
		String con = "";
		String fn = "";
		int aid = Integer.parseInt(request.getSession().getAttribute("m_id")
				.toString());
		// 查b君的id
		PrivateChatService pc = new PrivateChatService();
		int oid = pc.getOtherId(aid);
		if (oid != 0) {
			key = String.valueOf(aid) + "_" + String.valueOf(oid);
		}
		FileTransitService fs = new FileTransitService();
		status = fs.getBAnswer(key);
		String[] s = status.split("_");
		if (s.length > 1) {// 已经处理好
			con = s[1];
			fn = s[0];
			if (con.equals("3")) {
				meg = "对方不同意接收文件";
			} else if (con.equals("1")) {
				meg = "1";
			} else
				meg = "2";
			File f = new File("/" + fn);// 下载完了也好或者不同意也好，最后要把文件删掉
			f.delete();
		} else
			meg = "0";

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
