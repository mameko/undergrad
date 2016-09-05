<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

		<title>login</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/login.js"></script>

		<link rel="stylesheet" type="text/css" href="css/login.css" />
	</head>

	<body background="img/background_login.jpg">
	<form action="GetURoomAction.do?method=getURoom" id = "form1" name = "form1" method ="post">
		<a href="javascript:show_lt()" id="loginlink" class="a_text">login</a>
		<table id="logintable" bgcolor="#FFFFFF">
			<tr>
				<td>
					username:
				</td>
				<td colspan="2">
					<input type="text" id="username" />
				</td>
			</tr>
			<tr>
				<td>
					password:
				</td>
				<td colspan="2">
					<input type="password" id="password" />
				</td>
			</tr>
			<tr>
				<td>
					<a href="#" onClick="dosubmit()">submit</a>
				</td>
				<td>
					<a href="#" onClick="doreset()">reset</a>
				</td>
				<td>
					<a href="#" onClick="doback()">back</a>
				</td>
			</tr>
		</table>
		<a href="javascript:show_rt()" id="register" class="a_text">register</a>

		<table id="registertable" bgcolor="#FFFFFF">
			<tr>
				<td>
					username:
				</td>
				<td colspan="2">
					<input type="text" id="username_r" />
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
					description:
				</td>
				<td colspan="2">
					<textarea id="description_r"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<a href="#" onClick="dosubmit_r()">submit</a>
				</td>
				<td>
					<a href="#" onClick="doreset_r()">reset</a>
				</td>
				<td>
					<a href="#" onClick="doback_r()">back</a>
				</td>
			</tr>
		</table>

		<div id="message" style="background: aliceblue">
		</div>
</form>
	</body>
</html>
