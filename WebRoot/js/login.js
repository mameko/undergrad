function show_lt() {
	$("#logintable").fadeIn(3000);
	$("#register").fadeOut(3000);
}
function show_rt() {
	$("#loginlink").fadeOut(3000);
	$("#registertable").fadeIn(3000);
}
function hidet() {
	$("#logintable").fadeOut(3000);
}
function doreset() {
	$("#username").attr("value", "");
	$("#password").attr("value", "");
}
function dosubmit() {
	var name = $("#username").val();
	var ps = $("#password").val();
	$.post("LoginAction.do?method=check", {"username":name, password:ps}, cb);//can use without ""
}
function cb(data) {
	$("#message").html(data);
	doshowmessage();
	if (data.trim() == "\u767b\u5f55\u6210\u529f") {//trim用来去除多余空格函数 
		hidet();
		$("#loginlink").fadeOut(3500);
		setTimeout("jump()", 3400);
	}
		
}
function doreset_r() {
	$("#username_r").attr("value", "");
	$("#password_r").attr("value", "");
	$("#description_r").attr("value", "");
}
function dosubmit_r() {
	var name = $("#username_r").val();
	var ps = $("#password_r").val();
	var des = $("#description_r").val();
	$.post("RegisterAction.do?method=regist", {"username_r":name, password_r:ps, description_r:des}, cb_r);//can use without ""
}
function cb_r(data) {
	$("#message").html(data);//呢句知解定写if段面
	doshowmessage();		 //两5点一要系个上？
	if (data.trim() == "\u6ce8\u518c\u6210\u529f" || data.trim() == "\u6ce8\u518c\u5931\u8d25") {//trim用来去除多余空格函数
		hidet();
		$("#loginlink").fadeOut(3500);
		$("#register").fadeOut(3500);
		$("#registertable").fadeOut(3500);
		setTimeout("jumplogin()", 3400);
	}
}
function jump() {
	document.form1.submit();
}
function jumplogin() {
	self.location = "login.jsp";
}
function doback() {
	$("#logintable").fadeOut(3000);
	$("#register").fadeIn(3000);
}
function doback_r() {
	$("#registertable").fadeOut(3000);
	$("#loginlink").fadeIn(3000);
}
function doshowmessage() {
	$("#message").show();
}