package com.yourcompany.struts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yourcompany.struts.form.ChatMessage;

public class ChatService {// 其实可以用hashmap入边装arraylist实现，hashmap的key系房间号，arraylist系通话记录
	// 然后另外再用一个hashmap加arraylist来装5同房间的用户，同样hashmap的key系房间号，arraylist用户id
	// 悭翻个userRoomMap，d对应关系又没甘复杂，不过依然需要recordPlace这个hashmap来保存用户在房间中发言记录的位置。
	// 但是显然这种比现在这里的这种好，因为可以扩展。上面这种方法在privatechatservice中会尝试使用。
	// 突然间飙童淋到的～
	static List<ChatMessage> chatRoom1Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());// 每个公共房50条通话记录
	static List<ChatMessage> chatRoom2Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());
	static List<ChatMessage> chatRoom3Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());
	static List<ChatMessage> chatRoom4Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());
	static List<ChatMessage> chatRoom5Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());

	static List<ChatMessage> URoom1Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());// 每个客房50条通话记录
	static List<ChatMessage> URoom2Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());
	static List<ChatMessage> URoom3Message = Collections
			.synchronizedList(new ArrayList<ChatMessage>());

	static List<Integer> chatRoom1Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录chatroom1里的用户的id，.size()为该聊天室的人数。
	static List<Integer> chatRoom2Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录chatroom2里的用户
	static List<Integer> chatRoom3Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录chatroom3里的用户
	static List<Integer> chatRoom4Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录chatroom4里的用户
	static List<Integer> chatRoom5Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录chatroom5里的用户

	static List<Integer> ur1Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录ur1里的用户
	static List<Integer> ur2Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录ur2里的用户
	static List<Integer> ur3Ulist = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录ur3里的用户

	static Map<Integer, Integer> recordPlace = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());// 记录用户所需返回的记录在总记录中的地方.hashmap<id,list的位置>，超过50条记录后要经常修改list的位置。
	static List<Integer> userRoomMap = Collections
			.synchronizedList(new ArrayList<Integer>());// 记录用户房号r_id与urlist的对应关系

	public int getUNumber(int rid) {// 查询房间人数
		if (rid <= 5) {
			switch (rid) {
			case 1:
				return chatRoom1Ulist.size();
			case 2:
				return chatRoom2Ulist.size();
			case 3:
				return chatRoom3Ulist.size();
			case 4:
				return chatRoom4Ulist.size();
			case 5:
				return chatRoom5Ulist.size();
			}
		} else {
			int ur = userRoomMap.indexOf(rid);
			switch (ur) {
			case 0:
				return ur1Ulist.size();
			case 1:
				return ur2Ulist.size();
			case 2:
				return ur3Ulist.size();
			}
		}
		return 0;
	}

	public boolean addUserToRoomList(int r_id, int m_id) {// 把用户加入到房间里面
		if (r_id > 5) {
			int i = userRoomMap.indexOf(r_id);
			switch (i) {
			case 0:
				if (ur1Ulist.contains(m_id)) {
				} else {
					ur1Ulist.add(m_id);
					recordPlace.put(m_id, URoom1Message.size());
				}
				break;
			case 1:
				if (ur2Ulist.contains(m_id)) {
				} else {
					ur2Ulist.add(m_id);
					recordPlace.put(m_id, URoom2Message.size());
				}
				break;
			case 2:
				if (ur3Ulist.contains(m_id)) {
				} else {
					ur3Ulist.add(m_id);
					recordPlace.put(m_id, URoom3Message.size());
				}
				break;
			}
		} else {// 公共房间
			switch (r_id) {
			case 1:
				if (chatRoom1Ulist.contains(m_id)) {
				} else {
					chatRoom1Ulist.add(m_id);
					recordPlace.put(m_id, chatRoom1Message.size());// 一开始进入聊天室时，从最后一条记录开始接受
				}
				break;
			case 2:
				if (chatRoom2Ulist.contains(m_id)) {
				} else {
					chatRoom2Ulist.add(m_id);
					recordPlace.put(m_id, chatRoom2Message.size());
				}
				break;
			case 3:
				if (chatRoom3Ulist.contains(m_id)) {
				} else {
					chatRoom3Ulist.add(m_id);
					recordPlace.put(m_id, chatRoom3Message.size());
				}
				break;
			case 4:
				if (chatRoom4Ulist.contains(m_id)) {
				} else {
					chatRoom4Ulist.add(m_id);
					recordPlace.put(m_id, chatRoom4Message.size());
				}
				break;
			case 5:
				if (chatRoom5Ulist.contains(m_id)) {
				} else {
					chatRoom5Ulist.add(m_id);
					recordPlace.put(m_id, chatRoom5Message.size());
				}
				break;
			}
		}
		return true;
	}

	public void writeMessageInList(ChatMessage cm) {// 把用户所发的信息放到message pool里
		int r_id = cm.getRid();
		if (r_id > 5) {
			int c = userRoomMap.indexOf(r_id);
			switch (c) {
			case 0:
				if (isFull(URoom1Message)) {
					URoom1Message.remove(0);
					for (int i = 0; i < ur1Ulist.size(); i++) {
						int mid = ur1Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}
				URoom1Message.add(cm);
				break;
			case 1:
				if (isFull(URoom2Message)) {
					URoom2Message.remove(0);
					for (int i = 0; i < ur2Ulist.size(); i++) {
						int mid = ur2Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}
				URoom2Message.add(cm);
				break;
			case 2:
				if (isFull(URoom3Message)) {
					URoom3Message.remove(0);
					for (int i = 0; i < ur3Ulist.size(); i++) {
						int mid = ur3Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}
				URoom3Message.add(cm);
				break;
			}
		} else {// 公共房间
			switch (r_id) {
			case 1:
				if (isFull(chatRoom1Message)) {// 判断房间的message
					// pool是否已经够50条记录，如果够了的话，
					chatRoom1Message.remove(0);// 删除第一条记录后再把新纪录加入.

					// 将所有用户的取信息位置减少一位
					for (int i = 0; i < chatRoom1Ulist.size(); i++) {
						int mid = chatRoom1Ulist.get(i);// 取得该房间中的用户id
						int value = recordPlace.get(mid);// 得到用户的记录位置
						if (value != 0) {// 判断是否为0，如果已经为0，就不用减一
							recordPlace.put(mid, value - 1);
						}
					}
				}
				chatRoom1Message.add(cm);// 把用户所发信息放入该聊天室的message pool
				break;
			case 2:
				if (isFull(chatRoom2Message)) {
					chatRoom2Message.remove(0);
					for (int i = 0; i < chatRoom2Ulist.size(); i++) {
						int mid = chatRoom2Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}

				chatRoom2Message.add(cm);
				break;
			case 3:
				if (isFull(chatRoom3Message)) {
					chatRoom3Message.remove(0);
					for (int i = 0; i < chatRoom3Ulist.size(); i++) {
						int mid = chatRoom3Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}
				chatRoom3Message.add(cm);
				break;
			case 4:
				if (isFull(chatRoom4Message)) {
					chatRoom4Message.remove(0);
					for (int i = 0; i < chatRoom4Ulist.size(); i++) {
						int mid = chatRoom4Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}
				chatRoom4Message.add(cm);
				break;
			case 5:
				if (isFull(chatRoom5Message)) {
					chatRoom5Message.remove(0);
					for (int i = 0; i < chatRoom5Ulist.size(); i++) {
						int mid = chatRoom5Ulist.get(i);
						int value = recordPlace.get(mid);
						if (value != 0) {
							recordPlace.put(mid, value - 1);
						}
					}
				}
				chatRoom5Message.add(cm);
				break;
			}
		}
	}

	boolean isFull(List<ChatMessage> room1Message) {// 判断message
		// pool是否已满
		if (room1Message.size() > 50) {
			return true;
		} else
			return false;
	}

	public ArrayList<ChatMessage> getMessageInList(int r_id, int m_id) {// 从message
		// pool里面取聊天记录显示给用户
		ArrayList<ChatMessage> cmlett = new ArrayList<ChatMessage>();
		if (r_id > 5) {
			int c = userRoomMap.indexOf(r_id);
			switch (c) {
			case 0:
				int up1 = recordPlace.get(m_id);
				for (int i = up1; i < URoom1Message.size(); i++) {
					cmlett.add(URoom1Message.get(i));
				}
				break;
			case 1:
				int up2 = recordPlace.get(m_id);
				for (int i = up2; i < URoom2Message.size(); i++) {
					cmlett.add(URoom2Message.get(i));
				}
				break;
			case 2:
				int up3 = recordPlace.get(m_id);
				for (int i = up3; i < URoom3Message.size(); i++) {
					cmlett.add(URoom3Message.get(i));
				}
				break;
			}
		} else {// 公共房间
			switch (r_id) {
			case 1:
				int pp1 = recordPlace.get(m_id);
				for (int i = pp1; i < chatRoom1Message.size(); i++) {
					cmlett.add(chatRoom1Message.get(i));
				}
				break;
			case 2:
				int pp2 = recordPlace.get(m_id);
				for (int i = pp2; i < chatRoom2Message.size(); i++) {
					cmlett.add(chatRoom2Message.get(i));
				}
				break;
			case 3:
				int pp3 = recordPlace.get(m_id);
				for (int i = pp3; i < chatRoom3Message.size(); i++) {
					cmlett.add(chatRoom3Message.get(i));
				}
				break;
			case 4:
				int pp4 = recordPlace.get(m_id);
				for (int i = pp4; i < chatRoom4Message.size(); i++) {
					cmlett.add(chatRoom4Message.get(i));
				}
				break;
			case 5:
				int pp5 = recordPlace.get(m_id);
				for (int i = pp5; i < chatRoom5Message.size(); i++) {
					cmlett.add(chatRoom5Message.get(i));
				}
				break;
			}
		}
		return cmlett;
	}

	public void setUserRoomMap(int r_id) {// 添加房间时，把用户房间与messagelist的对应关系建立在userRoomMap表中
		if (userRoomMap.size() > 3) {
			System.out.println("userRoomMap out of boundery");
		} else {
			if (userRoomMap.contains(r_id)) {
				System.out.println("userRoomMap mapping error");
			} else {
				userRoomMap.add(r_id);
			}
		}
	}

	public void resetUserRoomMap(int r_id) {// 删除房间时，把用户房间与message
		// pool的对应关系也在userRoomMap表中删除
		if (userRoomMap.contains(r_id)) {
			int i = userRoomMap.indexOf(r_id);
			switch (i) {// 把message pool清空
			case 0:
				ur1Ulist.clear();
				break;
			case 1:
				ur2Ulist.clear();
				break;
			case 2:
				ur3Ulist.clear();
				break;
			}
			userRoomMap.remove(i);// 把对应关系清空
		}
	}

	public void initUserRoomMap(int r_id) {// 初始化userRoomMap
		userRoomMap.add(r_id);
	}

	public void quitRoom(int r_id, Integer m_id) {// 退出房间时把用户在该房间的用户列表中删除，也把用户与message
		// pool中位置的对应关系recordplace给删除
		recordPlace.remove(m_id);
		if (r_id > 5) {
			int c = userRoomMap.indexOf(r_id);
			switch (c) {
			case 0:
				ur1Ulist.remove(m_id);
				break;
			case 1:
				ur2Ulist.remove(m_id);
				break;
			case 2:
				ur3Ulist.remove(m_id);
				break;
			}
		} else {
			switch (r_id) {
			case 1:
				chatRoom1Ulist.remove(m_id);
				break;
			case 2:
				chatRoom2Ulist.remove(m_id);
				break;
			case 3:
				chatRoom3Ulist.remove(m_id);
				break;
			case 4:
				chatRoom4Ulist.remove(m_id);
				break;
			case 5:
				chatRoom5Ulist.remove(m_id);
				break;
			}
		}
	}

	public List<Integer> getUserlist(int r_id) {// 取用户列表
		if (r_id > 5) {
			int c = userRoomMap.indexOf(r_id);
			switch (c) {
			case 0:
				return ur1Ulist;
			case 1:
				return ur2Ulist;
			case 2:
				return ur3Ulist;
			}
		} else {
			switch (r_id) {
			case 1:
				return chatRoom1Ulist;
			case 2:
				return chatRoom2Ulist;
			case 3:
				return chatRoom3Ulist;
			case 4:
				return chatRoom4Ulist;
			case 5:
				return chatRoom5Ulist;
			}
		}
		return null;
	}
}