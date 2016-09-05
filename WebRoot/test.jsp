<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

		<title>My JSP 'test.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
 /*var fontS;
 var fontN;
 var fontC;
 var fontFormatB;//1的时候为选中
 var fontFormatI;
 var fontFormatU;
 
 function changeSize(){//改大小
  	var fontSize = document.getElementById("siz");
 	var e = window.frames["mye"]; 		
 	e.document.execCommand("fontSize",false,fontSize.options[fontSize.selectedIndex].text);
 	fontS = fontSize.options[fontSize.selectedIndex].text;
 	e.focus();
 }
 
 function changeColor(){//改颜色
 	 var fontColor = document.getElementById("col");
 	 var e = window.frames["mye"];
 	 e.document.execCommand("ForeColor",false,fontColor.options[fontColor.selectedIndex].text);
 	 fontC = fontColor.options[fontColor.selectedIndex].text;
 	 e.focus();
 }
function changeFontName(){//改字体
var fontName = document.getElementById("nam");
 		var e = window.frames["mye"]; 	
		e.document.execCommand("FontName",false,fontName.options[fontName.selectedIndex].text);
		fontN = fontName.options[fontName.selectedIndex].text;
		e.focus();
 }
 
 function dosubmit(){
 	var e = window.frames["mye"]; 		
	$.post("TestAction.do?method=getMessage",{message:e.document.body.innerHTML},showmeg);//post5乱码
	e.document.body.innerHTML = "";	
	e.document.execCommand("FontSize",false,fontS);//把保存好的用户属性设好，大小
 	e.document.execCommand("FontName",false,fontN);//字体
 	e.document.execCommand("ForeColor",false,fontC);//颜色
 	if(fontFormatB==1)
			e.document.execCommand("Bold",null,null);
	if(fontFormatI == 1)
 			e.document.execCommand("Italic",null,null);
  	if(fontFormatU ==1)
  			e.document.execCommand("Underline",null,null); 	
 	e.focus(); 	
 }
 
 function changeFomat(f){//粗细，斜体，下划线 	
 var e = window.frames["mye"];//""入边要系iframe个死人名
	if(f=="Bold"){
		if(fontFormatB==1){
			fontFormatB = 0;
		}else
			fontFormatB = 1;
 	}else{
 		if(f == "Italic"){
 			if(fontFormatI==1){
 				fontFormatI=0;
 			}else
 				fontFormatI=1;
  		}else{
  			if(f == "Underline"){
	  			if(fontFormatU==1){
	 				fontFormatU=0;
	 			}else
	 				fontFormatU=1;
	  			}
  		}		
 	}
 	e.document.execCommand(f,null,null);//个死人firefox要写埋后面个个，，
 	e.focus();
 }
 */
 function showmeg(data){
 	var e = window.frames["mye"];
 	$("#display").html(data);
	e.focus();
 }
 
 function addFace(imagenum){
	var num = imagenum.id;
 	var e = window.frames["mye"]; 
  	e.document.execCommand("InsertImage",false,"img/"+num+".gif");
  	$("#facelist").slideUp("slow");
  	flag = 0;
 	e.focus();
}
var imgflag = 0;
function dosubmit(){
	var e = window.frames["mye"];
	$.post("TestAction.do?method=getMessage",{message:e.document.body.innerHTML},showmeg);
}

function showFacelist(event){
	if(imgflag == 0){
		imgflag = 1;
		$("#facelist").slideDown("slow");
	}else{
		imgflag = 0;
		$("#facelist").slideUp("slow");
	}
}

</script>

<style type="text/css">
#facelist {
	display: none;
	position: absolute;
	top: 50px;
	left: 50px;
}
</style>
	</head>

	<body>
		Myeditor
		<br>
		<div id="chatfun">
			<!--			字体:
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
				<input type="button" value="B" onclick="changeFomat('Bold')">
				<input type="button" value="I" onclick="changeFomat('Italic')">
				<input type="button" value="U" onclick="changeFomat('Underline')">
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
				</select> -->
		<input type="button" value="magicface" onclick="showFacelist(event)" /> 
			<div id="facelist">
				<table>
					<tr>
						<td>
							<a onclick = "addFace(this)" id = "0"><img src="img/0.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)" id = "5"><img src="img/5.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "4"><img src="img/4.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "14"><img src="img/14.gif" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a onclick = "addFace(this)"  id = "1"><img src="img/1.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "6"><img src="img/6.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)" id = "9"><img src="img/9.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)" id = "15"><img src="img/15.gif" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a onclick = "addFace(this)"  id = "2"><img src="img/2.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "7"><img src="img/7.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "40"><img src="img/40.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "11"><img src="img/11.gif" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a onclick = "addFace(this)" id = "3"><img src="img/3.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "8"><img src="img/8.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "13"><img src="img/13.gif" /></a>
						</td>
						<td>
							<a onclick = "addFace(this)"  id = "12"><img src="img/12.gif" /></a>
						</td>
					</tr>
				</table>
			</div> 
		</div>
		<div>
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

		<input type="button" value="send" onclick="dosubmit()">
		<p>
		<div id="display">
			fagar
		</div>

	</body>
</html>
