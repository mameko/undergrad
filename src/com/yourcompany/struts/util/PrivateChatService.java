package com.yourcompany.struts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.yourcompany.struts.form.ChatMessage;

public class PrivateChatService {
	static List<String> questList = Collections.synchronizedList(new ArrayList<String>());// 记录哪两个用户在私聊，形式为
	// 用户Aid_用户Bid_同意聊天（yes或者no）与否(A用户请求与B用户聊天)
	static Map<String, ArrayList<ChatMessage>> messagePool = Collections.synchronizedMap(new HashMap<String, ArrayList<ChatMessage>>());
	// 呢个hashmap就吧闭咯，用a君个id同b君的id合体做key（具体形式：b君将条也插入a君的后面，-。-,aid_bid。其中aid<bid),个Arraylist入边就装距哋的聊天信息。每次就甘按顺序循环就可以摞到ab君的爱情结晶。
	public int getFlag(String bid) {// 把请求列表（questlist）中的数据取出，循环地取出被请求者的id与bid比较，
		for (int i = 0; i < questList.size(); i++) {// 如果相同就表示有人请求
			String record = (String) questList.get(i);
			String[] id = record.split("_");// 把一条record以“_”为界分开几份
			if (id[1].equals(bid)) {
				return Integer.parseInt(id[0]);
			}
		}
		return 0;
	}

	public int setFlag(String bid, String answer) {// 把请求列表（questlist）中的数据取出，循环地取出被请求者的id与bid比较，把b用户的回复放到questlist中以供a用户查询
		for (int i = 0; i < questList.size(); i++) {// 如果相同就表示有人请求
			String record = questList.get(i);
			String[] id = record.split("_");// 把一条record以“_”为界分开几份
			if (id[1].equals(bid)) {
				record = record + answer;
				questList.remove(i);
				questList.add(record);
				return 1;// 找到，并且设好了
			}
		}
		return 0;// 找不到用户，设置失败
	}

	public int setQuestList(int aid, int bid) {// 设置请求私聊列表
		String Aid = String.valueOf(aid);
		String Bid = String.valueOf(bid);
		String record = "";
		String record1 = "";
		String record2 = "";
		String record3 = "";
		String record4 = "";
		String record5 = "";

		record = Aid + "_" + Bid + "_";
		record1 = Aid + "_" + Bid + "_" + "yes";
		record2 = Aid + "_" + Bid + "_" + "no";

		record3 = Bid + "_" + Aid + "_" + "yes";
		record4 = Bid + "_" + Aid + "_" + "no";
		record5 = Bid + "_" + Aid + "_";
		if (questList.contains(record) || questList.contains(record3)) {
			return 1;// 已在请求中，请稍候
		} else {
			if (questList.contains(record1) || questList.contains(record2)
					|| questList.contains(record4)
					|| questList.contains(record5)) {
				return 2;// 已在聊天
			}
		}

		if (messagePool.size() > 1000) {
			return 3;// 没位，只提供1000对cp的位置
		} else {
			questList.add(record);
			return 0;// 把聊天请求设置好，请求中，请稍候。
		}

	}

	public int getAns(String aid) {// a用户轮询questlist，看自己的聊天请求是否被答应了
		for (int i = 0; i < questList.size(); i++) {
			String record = questList.get(i);
			String[] id = record.split("_");// 把一条record以“_”为界分开几份
			if (id[0].equals(aid)) {
				if (id.length < 3) {
					return 2;
				} else {
					if (id[2].equals("yes")) {// 同意聊天
						return 1;
					} else {
						questList.remove(i);// 因为b君不同意a君的聊天要求，所以从questlist中把a君向b君的邀请记录删掉
					}
					break;
				}
			}
		}
		return 0;
	}

	public ArrayList<ChatMessage> getMessageInList(String abid) {// 从messagepool里面取聊天记录显示给用户
		ArrayList<ChatMessage> cmlett = new ArrayList<ChatMessage>();
		String qkey1 = "";// 组织好查询questlist的key
		String qkey2 = "";
		String[] qkeys = abid.split("_");
		if (qkeys.length != 0) {
			qkey1 = qkeys[0] + "_" + qkeys[1] + "_yes";
			qkey2 = qkeys[1] + "_" + qkeys[0] + "_yes";

			if (questList.contains(qkey1) || questList.contains(qkey2)) {// 如果两方中的一方退出了私聊的话，这里就会为空，所以这句是判断对方是否退出了。
				if (messagePool.get(abid) == null) {// 第一次时创建聊天信息
					ArrayList<ChatMessage> cml = new ArrayList<ChatMessage>();
					ChatMessage cm = new ChatMessage();
					cm.setName("");
					cm.setTime("");
					cm.setMessage("可以开始私聊了");
					cml.add(cm);
					messagePool.put(abid, cml);
				}
				cmlett = messagePool.get(abid);
			} else
				cmlett = null;
		}
		return cmlett;
	}

	public int getOtherId(int aorbid) {
		String aorb = String.valueOf(aorbid);
		for (int i = 0; i < questList.size(); i++) {// 如果相同就表示有人请求
			String record = questList.get(i);
			String[] ids = record.split("_");// 把一条record以“_”为界分开几份
			if (ids[0].equals(aorb)) {
				return Integer.parseInt(ids[1]);
			} else {
				if (ids[1].equals(aorb)) {
					return Integer.parseInt(ids[0]);
				}
			}
		}
		return 0;
	}

	public void writeMessageInList(String abid, ChatMessage cm) {// 把用户所发的信息放到messagepool里
		String qkey1 = "";// 组织好查询questlist的key
		String qkey2 = "";
		String[] qkeys = abid.split("_");
		if (qkeys.length != 0) {
			qkey1 = qkeys[0] + "_" + qkeys[1] + "_yes";
			qkey2 = qkeys[1] + "_" + qkeys[0] + "_yes";

			if (questList.contains(qkey1) || questList.contains(qkey2)) {// 如果不在请求列表中，那么就是ab君中某一方已经退出私聊，在这里就不能再创建列表啦
				if (messagePool.get(abid).size() > 50) {// 每对cp只保存最近50条记录
					messagePool.get(abid).remove(0);
				}
				messagePool.get(abid).add(cm);
			}
		}
	}

	public void quitPc(String abid) {// 退出私聊时清除所占用的资源
		messagePool.remove(abid);
		String qkey1 = "";// 组织好查询questlist的key
		String qkey2 = "";
		String[] qkeys = abid.split("_");
		if (qkeys.length != 0) {
			qkey1 = qkeys[0] + "_" + qkeys[1] + "_yes";
			qkey2 = qkeys[1] + "_" + qkeys[0] + "_yes";

			questList.remove(qkey1);
			questList.remove(qkey2);
		}
	}	
}
