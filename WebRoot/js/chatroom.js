// JavaScript Document
$(window).load(getMessageFromServer);//当页面载入时启动取数据函数（所发信息）
$(window).load(twentyMinutesMinder);//20分钟提醒
$(window).load(getUserlistFromServer);//当页面载入时启动取数据函数（用户列表）
$(window).load(someoneCallyou);//轮询服务器看有没有人叫你
$(window).load(someoneSendyouThing);//轮询服务器看有没有人发东西给你
//$(window).unload(onquit);//用户按关闭按钮关闭浏览器时，把有关的session属性remove掉
var flag=0;//判断现在是否私聊中，如果=1 私聊，=0 群聊；
function getMessageFromServer() {//去数据的函数
	if(flag == 0){
		$.post("ChatroomAction.do?method=getMessage", null, showmeg);//群聊
	}else{//正式开始私聊，从服务器那边拿messagepool的消息
		$.post("PrivateChatAction.do?method=getMessage", null, showmeg);//私聊
	}
}
var t3;
function showmeg(data) {
	$("#displayarea").html(data);
	document.getElementById("displayarea").scrollTop=10000000;//滚动条滚到最低 
	t3 = setTimeout("getMessageFromServer();", 5000);//设置每隔5秒钟到服务器取一次所发信息的数据	
}

function onback() {	
	clearTimeout(t);
	clearTimeout(t1);
	clearTimeout(t3);
	clearTimeout(t4);
	clearTimeout(t5);
	clearTimeout(t6);
	clearTimeout(t7);
	clearTimeout(t8);
	if(flag == 0){//群聊的退出	
		window.location.href = "ChooseRoomAction.do?method=quitRoom";//退出房间时执行，把session中与该用户相关的东西删掉
	}else{
		flag = 0;//退出私聊
		window.location.href = "PrivateChatAction.do?method=quit";//清理好资源后跳转去chooseroom.jsp
	}
}
function sendMessage() {//把信息发到服务器
	twentyMinutesMinder();
	var e = window.frames["mye"]; 	
	if(flag == 0){
		$.post("ChatroomAction.do?method=sendMessage", {meg:e.document.body.innerHTML}, showmeg);//群聊
	}else
		$.post("PrivateChatAction.do?method=sendMessage", {meg:e.document.body.innerHTML}, showmeg);//私聊	
		
	//post5乱码
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
var t;
var t1;
function twentyMinutesMinder() {//20分钟提醒用户发送信息
	clearTimeout(t);
	t = setTimeout("alert('20分钟啦，再不发言就踢你哦');thirtyMinutesQuit();", 1200000);
}
function thirtyMinutesQuit() {//30分钟都不发的话就被踢出房间
	clearTimeout(t1);
	t1 = setTimeout("onback();", 1800000);
}

function getUserlistFromServer(){//向服务器取用户列表
	if(flag == 0){
		$.post("ChatroomAction.do?method=getUserlist", null, showmeg1);//群聊
	}
}
var t4;
function showmeg1(data) {
	$("#clientlist").html(data);
	if(flag == 0){
		t4 = setTimeout("getUserlistFromServer();", 10000);//设置每隔10秒钟到服务器取一次用户列表数据
	}else{
		$("#clientlist").html("");
	}
}

function onquit(){//退出本站时的动作，本来在这里退出是不好的，但是用户可能会关闭窗口
	onback();
	$.post("ChooseRoomAction.do?method=quit",null,callback);	
}	

function callback(){//上面函数的callback
	self.location = "login.jsp";
}

function getdescription(mid){//获取用户description
	$.post("MemberAction.do?method=getUserDes", {m_id:mid}, showpop);
}

function showpop(data){//鼠标放到用户列表中的用户名时把用户的description信息显示出来
$("#des").html(data);
}

function onpop(event,mid){//把显示的div的位置设置为鼠标的右下方
	$("#des").show("slow").css("position","absolute").css("top",event.clientY+ "px")
          .css("left",event.clientX + "px");
    getdescription(mid);         
}

function stoppop(){//鼠标移开后隐藏用户的description信息
	$("#des").hide("slow");
}
var id;//把b君的id记录下来
function twopeoplechat(bid){//点击用户列表中的用户名时，请求与该用户聊天，发送请求信息给该用户。
	id = bid;
	$.post("PrivateChatAction.do?method=setChatQuest", {b_id:bid}, showmeg3);
}

function showmeg3(data){//显示等待信息,开始轮询对方答复
	$("#invite").show("slow");
	$("#invite").html(data);
	questAns();
}

function questAns(){//轮询对方答复
	$.post("PrivateChatAction.do?method=getAnswer", {b_id:id}, showmeg5);
}
var t6;
function showmeg5(data){	
	if(data.trim()=="accept"){//同意聊天，a君扮跳转
		flag =1;//设置私聊标志
		clearTimeout(t6);//不用再轮询答案
		clearTimeout(t5);//不用再轮询是否有人叫你
		hidepcinfo();
		$.post("PrivateChatAction.do?method=pcPrepare");
	}else{
		if(data.trim()=="2"){//请求中，请稍候
			$("#invite").html("请求中，请稍候");
			t6 = setTimeout("questAns();", 6000);//每6秒轮询服务器一次看b君有反应没有
		}else
			$("#invite").html(data);
		$("#invite").show("slow");		
	}	
}

function someoneCallyou(){//轮询服务器，看是否有人叫你私聊
	$.post("PrivateChatAction.do?method=getFlag", null, showmeg2);
}
var t5;
function showmeg2(data) {
	if(data.trim()!=""){//有人叫
		$("#invitation").show("slow");//用slideUp5得
		$("#invite").show();	
		$("#invite").html(data);			
	}
	t5 = setTimeout("someoneCallyou();", 6000);//设置每隔10分钟到服务器轮询一次看有没有人叫你
}

function hidepcinfo(){//被呼叫用户选择是否同意聊天后，把邀请私聊的信息框隐藏起来
	$("#invitation").hide();
	$("#invite").hide();
}

function cancelpc(){//b君拒绝a君的谈话要求	
	hidepcinfo();
	$.post("PrivateChatAction.do?method=setFlag",{answer:"no"},showmeg7);	
}

function showmeg7(data){
	if(data.trim()=="ok"){//设置成功
	}else//用户不存在
	{
		$("#invite").html("错误发生，私聊终止");
		$("#invite").show("slow");
		$("#invite").hide("slow");
	}
}

function pcprepare(){//设置questlist,b君答应请求，设好questlist后去准备私聊
	hidepcinfo();	
	$.post("PrivateChatAction.do?method=setFlag", {answer:"yes"}, showmeg6);	
}

function showmeg6(data){//b君准备私聊
	if(data.trim()=="ok"){//设置成功
		flag = 1;//设置私聊标志
		clearTimeout(t5);//不用再轮询是否有人叫你
		$.post("PrivateChatAction.do?method=pcPrepare");
	}else{
		$("#invite").html("错误发生，私聊终止");
		$("#invite").show("slow");
		$("#invite").hide("slow");
	}
} 
var oc = 0;//记录状态 =1 打开， =0 关闭
function openfunctionlist(){//显示功能列表	
	if(flag == 1){
		if(oc == 0){
			$("#functions").slideDown("slow");
			oc = 1;
		}else{
			$("#functions").slideUp("slow");
			oc = 0;
		}		
	}else
		alert("现在还未开通公共聊天室能使用的功能");
}

function functionOneAct(){//打开上传文件的div
	$("#upl").show("slow");
}

function sureUpload(){//a君选择好文件以后按确定所执行的动作
	document.form1.submit();//把文件上传到web的根目录
	$("#upl").hide("slow");//把上传的div隐藏起来
	questAns2();
//	$.post("FileTransitAction.do?method=setFileList");//set一下文件列表，记录a君已经把文件上传了，即已经“发给”b君了
}

function cancelUpload(){//a君取消上传
	$("#upl").hide("slow");
}

function someoneSendyouThing(){//b君轮询看a君是否发了东西给自己
	$.post("FileTransitAction.do?method=getFileList", null, showmeg8);
}

function questAns2(){
	$.post("FileTransitAction.do?method=getAnswer", null, showmeg9);
}
var t8;
function showmeg9(data){
	if(data.trim()=="1"||data.trim()=="0"){//处理中，请等候	
		$("#setfr").html("处理中，请等候");
		t8 = setTimeout("questAns2();", 6000);//每6秒轮询服务器一次看b君有反应没有
	}else{
		if(data.trim()=="2"){//已接收
			$("#setfr").html("对方已接收文件");
		}else//	不同意接收	
			$("#setfr").html(data);
		clearTimeout(t8);
	}
	$("#setfr").show("slow");		
}
var t7;
function showmeg8(data) {
	if(data.trim()=="yes"){//a君有发也比b君
		$("#fileinfo").html("你有文件，要接收吗？");
		$("#downfr").show("slow");
		$("#downl").show("slow");
	}
	t7 = setTimeout("someoneSendyouThing();", 6000);//设置每隔10分钟到服务器轮询一次看有没有人发也比你
}

function sureDownload(){//b君确定要下载啦
	document.form2.submit();//b君下载web根目录上的文件
	$.post("FileTransitAction.do?method=setFileListStatus",{status:"agree"});
	$("#downl").hide("slow");
}

function refuseDownload(){//b君取消下载
	$.post("FileTransitAction.do?method=setFileListStatus",{status:"disagree"});
	$("#downl").hide("slow");
}

//===================================字体，颜色，大小，加粗，斜体，下划线====================================
 var fontS;//记录用户的preferences，大小
 var fontN;//字体
 var fontC;//颜色
 var fontFormatB;//1的时候为选中，粗体
 var fontFormatI;//斜体
 var fontFormatU;//下划线
 
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
 
  function addFace(imagenum){//添加表情
	var num = imagenum.id;
 	var e = window.frames["mye"]; 
  	e.document.execCommand("InsertImage",false,"img/"+num+".gif");
  	$("#facelist").slideUp("slow");
  	flag = 0;
 	e.focus();
}
var imgflag = 0;
function showFacelist(event){//显示表情列表
	if(imgflag == 0){
		imgflag = 1;
		$("#facelist").slideDown("slow");
	}else{
		imgflag = 0;
		$("#facelist").slideUp("slow");
	}
}