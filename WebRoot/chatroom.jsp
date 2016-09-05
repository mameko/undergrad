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
		<title>chatroom</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/chatroom.js"></script>
		<link rel="stylesheet" type="text/css" href="css/chatroom.css" />
	</head>

	<body background="img/19.jpg">
		<font color="background" style="font-family: Tahoma"><h2
				id="top" align="right">
				chatroom&nbsp;
			</h2> </font>
		<div id="round">
			<div id="displayarea" style="overflow: scroll;" scroll="200"></div>
			<div id="chatfun">
				字体:
				<select id="nam" onchange="changeFontName()">
					<option SELECTED="true">
					</option>
					<option>
						黑体
					</option>
					<option>
						仿宋_GB2312
					</option>
					<option>
						楷体_GB2312
					</option>
					<option>
						宋体
					</option>
					<option>
						Tahoma
					</option>
					<option>
						Comic Sans MS
					</option>
				</select>
				大小:
				<select id="siz" onchange="changeSize()">
					<option SELECTED="true">
					</option>
					<option>
						5
					</option>
					<option>
						6
					</option>
					<option>
						7
					</option>
					<option>
						8
					</option>
					<option>
						9
					</option>
					<option>
						10
					</option>
					<input type="button" value="B" onclick="changeFomat('Bold')"
						style="padding-left: 0px; padding-right: 0px;">
					<input type="button" value="I" onclick="changeFomat('Italic')"
						style="padding-left: 0px; padding-right: 0px;">
					<input type="button" value="U" onclick="changeFomat('Underline')"
						style="padding-left: 0px; padding-right: 0px;">
					颜色:
					<select id="col" onchange="changeColor()">
						<option SELECTED="true">
						</option>
						<option>
							red
						</option>
						<option>
							yellow
						</option>
						<option>
							blue
						</option>
						<option>
							green
						</option>
						<option>
							purple
						</option>
						<option>
							black
						</option>
					</select>

					<input type="button" value="表情"
						onclick="showFacelist(event)" />
					<div id="facelist">
						<table>
							<tr>
								<td>
									<a onclick="addFace(this)" id="0"><img src="img/0.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="5"><img src="img/5.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="4"><img src="img/4.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="14"><img src="img/14.gif" />
									</a>
								</td>
							</tr>
							<tr>
								<td>
									<a onclick="addFace(this)" id="1"><img src="img/1.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="6"><img src="img/6.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="9"><img src="img/9.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="15"><img src="img/15.gif" />
									</a>
								</td>
							</tr>
							<tr>
								<td>
									<a onclick="addFace(this)" id="2"><img src="img/2.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="7"><img src="img/7.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="40"><img src="img/40.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="11"><img src="img/11.gif" />
									</a>
								</td>
							</tr>
							<tr>
								<td>
									<a onclick="addFace(this)" id="3"><img src="img/3.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="8"><img src="img/8.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="13"><img src="img/13.gif" />
									</a>
								</td>
								<td>
									<a onclick="addFace(this)" id="12"><img src="img/12.gif" />
									</a>
								</td>
							</tr>
						</table>
					</div>
			</div>
			<div id="inputarea">
				<iframe src="k.htm" id="mye" name="mye">
					<html>
						<head>
							<meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
						</head>
						<body onload="document.designMode = 'on'" scroll="auto" id="ed">
						</body>
					</html>
				</iframe>
			</div>
			<br>
			<input type="button" id="send" onClick="sendMessage()" value="send">
		</div>
		<p id="buttom">
			&nbsp;
		</p>


		<font color="background" style="font-family: Tahoma"><h2
				id="clientlisttitle" align="center">
				online client
			</h2> </font>

		<div id="clientlist">
			<!-- <textarea id="onlineclient" readonly="" style="margin: 10px;">abc</textarea> -->
		</div>

		<a href="#" id="backlink" onClick="onback()"><h1>
				back
			</h1> </a>

		<div id="des">

		</div>

		<div id="invite">
			fawef
		</div>

		<div id="invitation">
			<input type="button" value="同意" onclick="pcprepare()">
			<input type="button" value="不同意" onclick="cancelpc()">
		</div>

		<a herf="#" id="functionlist" onclick="openfunctionlist()">want to</a>
		<div id="functions">
			<a herf="#" id="functionone" onclick="functionOneAct()">send file</a>
		</div>

		<div id="upl">
			<form action="exam506.jsp" id="form1" name="form1"
				enctype="multipart/form-data" method="post" target="hidden_frame">
				<input type="file" name="file" id="file" />
				<br>
				<input type="button" id="yes" value="确定" onclick="sureUpload()" />
				<input type="button" id="cancel" value="取消" onclick="cancelUpload()" />
				<iframe name="hidden_frame" id="hidden_frame" style="display: none"></iframe>
			</form>
		</div>

		<div id="setfr">
			abc
		</div>

		<div id="downl">
			<form action="download.jsp" id="form2" name="form2"
				enctype="multipart/form-data" method="post" target="hidden_frame">
				<div id="fileinfo"></div>
				<input type="button" id="agree" value="同意" onclick="sureDownload()" />
				<input type="button" id="disagree" value="不同意"
					onclick="refuseDownload()" />
			</form>
		</div>



		<!--<div id="back">
<a href="chatroom1.htm">chatroom1</a>
<a href="chatroom2.htm">chatroom2</a>
<a href="chatroom3.htm">chatroom3</a>
<a href="chatroom4.htm">chatroom4</a>
<a href="chatroom5.htm">chatroom5</a>
</div>-->

	</body>
</html>
