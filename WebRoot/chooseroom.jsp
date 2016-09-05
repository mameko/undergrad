<%@ page language="java" import="java.util.*,com.yourcompany.struts.form.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>chooseroom</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/chooseroom.js"></script>
		<link rel="stylesheet" type="text/css" href="css/chooseroom.css" />
	</head>

	<body background="img/background_login.jpg" onLoad="doinit()">
		<div id="chatroom1">
			<a class="a_text" href="#" id="1" onClick="checkvol(this)" onmouseout="jumpback1()"
				onMouseOver="jumpout1()" >chatroom1</a>
		</div>

		<div id="chatroom2">
			<a class="a_text" href="#" id="2" onmouseout="jumpback2()"
				onMouseOver="jumpout2()" onClick="checkvol(this)">chatroom2</a>
		</div>

		<div id="chatroom3">
			<a class="a_text" href="#" id="3" onmouseout="jumpback3()"
				onMouseOver="jumpout3()" onClick="checkvol(this)">chatroom3</a>
		</div>

		<div id="chatroom4">
			<a class="a_text" href="#" id="4" onmouseout="jumpback4()"
				onMouseOver="jumpout4()" onClick="checkvol(this)">chatroom4</a>
		</div>

		<div id="chatroom5">
			<a class="a_text" href="#" id="5" onmouseout="jumpback5()"
				onMouseOver="jumpout5()" onClick="checkvol(this)">chatroom5</a>
		</div>

		<div id="detail" style="background: aliceblue">
			123
		</div>
		<!--系服务器个边摞数据翻来ajax-->

		<div id="upperuserroom" href="#">
			<!--高级用户所创建的房间-->
			高级用户房间
			<%ArrayList<RoomForm> rfl = new ArrayList<RoomForm>();
			
			rfl = (ArrayList<RoomForm>)request.getAttribute("rfl"); 
		//	System.out.println(rfl.size());			
			for(int i = 0;i<rfl.size();i++){
		//	System.out.println(rfl.get(i).getR_id());	
			%>
			
			<a href = "#" id=<%=rfl.get(i).getR_id()%> onmouseout="hidedetail()" onMouseOver="showdetail(this)"
				onClick=checkvol(this)><%=rfl.get(i).getR_name() %></a>
			<br>
			<%} %>
		</div>

		<div id="messagebox">
			<!--进入需要密码房间时输入密码的空间-->
			请输入密码：
			<br>
			<input type="password" id="password">
			<br>
			<input type="submit" id="pwsubmit" value="submit" onClick="dosubmit_pw()">
			<input type="reset" id="pwreset" onClick="doreset_pw()" value="reset">
			<input type="button" id="pwback" onClick="doback_pw()" value="back">
		</div>

		<div id="room">
			<table id="roomdetail">
				<tr>
					<td>
						roomname:
					</td>
					<td colspan="2">
						<input type="text" id="roomname_r" />
					</td>
				</tr>
				<tr>
					<td>
						password:
					</td>
					<td colspan="2">
						<input type="password" id="password_r" />
					</td>
				</tr>
				<tr>
					<td>
						volumn:
					</td>
					<td colspan="2">
						<input type="text" id="volumn_r" />
					</td>
				</tr>
				<tr>
					<td>
						description:
					</td>
					<td colspan="2">
						<textarea id="description_r"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<a onClick="dosubmit_r()">submit</a>
					</td>
					<td>
						<a href="#" onClick="doreset_r()">reset</a>
					</td>
					<td>
						<a href="#" onClick="doback_r()">back</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="addordel_r">
			<a href="#" onClick="doaddr()">add</a>/
			<a href="#" onClick="dodelr()">delete</a>
		</div>
		<a href="#" class="a_text" id="back" onClick = "onquit()">quit</a>

		<table id="changeinfotable" bgcolor="#FFFFFF">
			<tr>
				<td>
					username:
				</td>
				<td colspan="2">
					<input type="text" id="username_c" />
				</td>
			</tr>
			<tr>
				<td>
					password:
				</td>
				<td colspan="2">
					<input type="password" id="password_c" />
				</td>
			</tr>
			<tr>
				<td>
					description:
				</td>
				<td colspan="2">
					<textarea id="description_c"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<a href="#" onClick="dosubmit_c()">submit</a>
				</td>
				<td>
					<a href="#" onClick="doreset_c()">reset</a>
				</td>
				<td>
					<a href="#" onClick="doback_c()">back</a>
				</td>
			</tr>
		</table>
		<a href="#" onClick="showchangetable()" id="changeinfo">change
			personal info</a>

		<div id = "roomtodel">

		</div>

	</body>
</html>
