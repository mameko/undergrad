package com.yourcompany.struts.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileTransitService {
	static Map<String, String> files = Collections
			.synchronizedMap(new HashMap<String, String>());// key为aid_bid，value为filename_状态，1--b君还未接受a君传上去了，2--b君接收以后，3--b君取消不肯接收

	public void setFileList(String stoset, String fn) {
		files.put(stoset, fn + "_1");
	}

	public String getFileListCond(String key) {// b君查询是否有人发东西给自己
		String status = "";
		String fn_con = files.get(key);
		if (fn_con != null) {
			status = fn_con;
		}
		return status;
	}
	
	public void setFileListCond(String key,String cond){//b君接受或者取消a君所发发文件时调用此函数记录情况
		String fn_con =files.get(key);
		String fn="";
		String con = "";
		String[] fc = fn_con.split("_");
		if(fc.length>1){
			fn = fc[0];
		}
		if(cond.equals("agree")){
			con = "_2";			
		}else
			con = "_3";
		files.put(key, fn+con);
	}
	
	public String getBAnswer(String key){//a君获取b君的反应
		String cond = "";
		String fn_con = files.get(key);
		if (fn_con != null) {
			cond = fn_con;
		}
		return cond;
	}
}
