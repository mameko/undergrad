<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/chooseroom.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function onpop(event){
			$("#pop").show("slow").css("position","absolute").css("top",event.clientY+ "px")
            .css("left",event.clientX + "px");
		}

		function stoppop(){
			$("#pop").hide();
		}

		</script>

		<style type="text/css">
			#pop{
				background-color: aliceblue;
				height: 50px;
				width: 50px;
				display: none;
				z-index:99;
			}
</style>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>

		<input id="jumpbutton" type="button" onmouseover="onpop(event)" onmouseout = "stoppop()"
			value="button" />
		<br>
		This is my JSP page.
		<div id="pop">
		123
		</div>
		<br>

	</body>
</html>