// JavaScript Document
//window.onunload=onquit();//用户按关闭按钮关闭浏览器时，把有关的session属性remove掉

function doinit(){//显示房间列表的动画
	$("#chatroom1").slideDown(1000);
    $("#chatroom2").slideDown(2000);
    $("#chatroom3").slideDown(3000);
    $("#chatroom4").slideDown(4000);
    $("#chatroom5").slideDown(5000);
	$("#back").slideDown(6000);
	}

function getinfo(num){//在onmouse时调用获取数据并返回，显示房间的情况
	$.post("ChooseRoomAction.do?method=showRoomInfo",{r_id:num},showmeg);
}

function showmeg(data){//在detail显示返回数据
	$("#detail").html(data);
}

function jumpout1(){//room1标签的动画
    var aj=$("#chatroom1");
//    $("#detail").slideUp("slow");
    $("#detail").show();
    aj.css("position","absolute").css("left","50px").css("top",aj.top+ "px");
	getinfo(1);
}

function jumpback1(){
    var aj=$("#chatroom1");
    // $("#detail").fadeOut("slow");
    $("#detail").hide();
    aj.css("position","absolute").css("left","10px").css("top",aj.top+ "px");
}

function jumpout2(){//room2标签的动画
  var aj=$("#chatroom2");
    $("#detail").show();
    aj.css("position","absolute").css("left","50px").css("top",aj.top+ "px");
    //$("#detail").slideUp("slow");
    getinfo(2);
}

function jumpback2(){
    var aj=$("#chatroom2")
            $("#detail").hide();;
    aj.css("position","absolute").css("left","10px").css("top",aj.top+ "px");
//    $("#detail").fadeOut("slow");
}

function jumpout3(){//room3标签的动画
  var aj=$("#chatroom3");
    $("#detail").show();
    aj.css("position","absolute").css("left","50px").css("top",aj.top+ "px");
    //$("#detail").slideUp("slow");
    getinfo(3);
}

function jumpback3(){
    var aj=$("#chatroom3");
    $("#detail").hide();
    aj.css("position","absolute").css("left","10px").css("top",aj.top+ "px");
    //$("#detail").fadeOut("slow");
}

function jumpout4(){//room4标签的动画
  var aj=$("#chatroom4");
    $("#detail").show();
    aj.css("position","absolute").css("left","50px").css("top",aj.top+ "px");
   // $("#detail").slideUp("slow");
   getinfo(4);
}

function jumpback4(){
    var aj=$("#chatroom4");
    $("#detail").hide();
    aj.css("position","absolute").css("left","10px").css("top",aj.top+ "px");
    //$("#detail").fadeOut("slow");
}

function jumpout5(){//room5标签的动画
  var aj=$("#chatroom5");
    $("#detail").show();
    aj.css("position","absolute").css("left","50px").css("top",aj.top+ "px");
    //$("#detail").slideUp("slow");
    getinfo(5);
}

function jumpback5(){
    var aj=$("#chatroom5");
    $("#detail").hide();
    aj.css("position","absolute").css("left","10px").css("top",aj.top+ "px");
    //$("#detail").fadeOut("slow");
}

function showdetail(obj){//用户建立的房间的信息获取
	var num = obj.id;
	 $("#detail").show();
	getinfo(num);
	
	}
	
function hidedetail(){//用户建立的房间的信息隐藏
	$("#detail").hide();
	}
	
function enterpassword(num){//进入用户所建立的房间前，检查是否需要密码。
//	var rid = obj.id;
	$.post("GetURoomAction.do?method=check",{r_id:num},showmeg8);	
	}

function showmeg8(data){//上面函数的callback
	if(data.trim()=="ok"){
		window.location.href="ChooseRoomAction.do?method=inRoomPre&r_id="+roomid;//在进入房间前做点准备
	}else
		$("#messagebox").show();
}	
	
function doreset_pw(){//输入密码的框的reset按钮的动作
	$("#password").attr("value","");
}

function doback_pw(){//输入密码的框的back按钮的动作
	$("#messagebox").hide();
}

function dosubmit_pw(){//输入密码的框的submit按钮的动作
	var pw = $("#password").val();
	$.post("GetURoomAction.do?method=checkpw",{password:pw},showmeg10);
}

function showmeg10(data){
	if(data.trim() == "ok"){
		window.location.href="ChooseRoomAction.do?method=inRoomPre&r_id="+roomid;//在进入房间前做点准备
	}else 
		showmeg2(data);
}
	
function doreset_r(){//用户新建房间填写的表格中的的reset按钮的动作
    $("#roomname_r").attr("value","");
	$("#password_r").attr("value","");
	$("#volumn_r").attr("value","");
	$("#description_r").attr("value","");
}

function doback_r(){//用户新建房间填写的表格中的的back按钮的动作
	$("#room").hide();
	$("#detail").hide();
}
	
function dosubmit_r(){//用户新建房间填写的表格中的的submit按钮的动作，提交给后台更新数据库
	var name = $("#roomname_r").val();
	var ps = $("#password_r").val();
	var des = $("#description_r").val();
	var vol = $("#volumn_r").val();
	$.post("RoomAction.do?method=add",{"roomname_r":name, password_r:ps, description_r:des,volumn_r:vol},showmeg4);
}
	
function showmeg4(data){//上面函数的callback
	showmeg2(data);
	$("#room").hide();
}
	
function doaddr(){//在添加房间时做检查，看用户是否能建房
	$.post("RoomAction.do?method=check",null,showmeg3);
}
		
function showmeg3(data){//上面函数的callback
	if(data.trim() == "ok"){
		$("#room").show();
	}else{
		showmeg2(data);
	}	
}
		
function dodelr(){//在删除房间时做检查，看用户是否删除房间
	$.post("RoomAction.do?method=check2",null,showmeg5);	
}

function showmeg5(data){//上面函数的callback
	if(data.trim() == "ok"){
		$("#roomtodel").show();
		$.post("RoomAction.do?method=getRoom",null,showmeg6);//检查可以删除的房间给用户选择删除哪个
	}else{
		showmeg2(data);
	}	
}

function showmeg6(data){//上面函数的callback
	$("#roomtodel").html(data);
}
	
function ondel(rid){//点击选中要删除的房间名时，提交给后台更新数据库
	$.post("RoomAction.do?method=delete",{r_id:rid},showmeg7);	
}

function showmeg7(data){//上面函数的callback
	showmeg2(data);
	$("#roomtodel").hide();
}
	
function showchangetable(){//更新个人信息按钮被点击时把要填的表格显示出来
	$("#changeinfo").hide();
	$("#changeinfotable").show();
}
	
function doreset_c() {//用户在更新个人信息时所填写的表格中的reset按钮的动作
	$("#username_c").attr("value", "");
	$("#password_c").attr("value", "");
	$("#description_c").attr("value", "");
}
	
function doback_c(){//用户在更新个人信息时所填写的表格中的back按钮的动作
	$("#changeinfo").show();
	$("#changeinfotable").hide();
	$("#detail").hide();
}
	
function dosubmit_c(){//用户在更新个人信息时所填写的表格中的submit按钮的动作，把资料提交后台更新数据库
	//	alert("click submit");
	var name = $("#username_c").val();
	var ps = $("#password_c").val();
	var des = $("#description_c").val();
	$.post("ChangeInfoAction.do?method=changeInfo",{"username_c":name, password_c:ps, description_c:des},showmeg2);
}
	
function showmeg2(data){//上面函数的callback
	$("#detail").show();
	$("#detail").html(data);
	$("#detail").hide(3000);
}
	
function onquit(){//退出本站时的动作
	$.post("ChooseRoomAction.do?method=quit",null,callback);	
}	

function callback(){//上面函数的callback
	self.location = "login.jsp";
}

var roomid;
function checkvol(obj){//检查用户是否能进入该房间
	roomid = obj.id;
	$.post("ChooseRoomAction.do?method=inRoomCheck",{r_id:roomid},showmeg9);
//	$.post("ChooseRoomAction.do?method=inRoomCheck",{r_id:roomid},shwomeg9);//5知点解甘rp就系用下边个距就5得左啦-_-#
}

function showmeg9(data){	
	if(data.trim() == "ok1"){
		//进入公共房间
		window.location.href="ChooseRoomAction.do?method=inRoomPre&r_id="+roomid;//在进入房间前做点准备
	}else{
		if(data.trim() == "ok2"){
		//进入用户房间前检查密码			
			enterpassword(roomid);			
		}else
			showmeg2(data);
	}	
}