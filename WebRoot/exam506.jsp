<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>
<%@page import="com.jspsmart.upload.*"%>
<body>
<%  
      SmartUpload upload=new SmartUpload();
	  upload.initialize(pageContext);
	  upload.upload();
	  Files  collection=upload.getFiles();
	  File file=null;
	  String fileName=null;
	  for(int i=0;i<collection.getCount();i++)
	  {
	      file=collection.getFile(i);
		  fileName=file.getFileName();
		  if(!file.isMissing())    
		  {  
		     file.saveAs("/"+fileName);
		     session.setAttribute("fileN",fileName);
		     RequestDispatcher go = request.getRequestDispatcher("FileTransitAction.do?method=setFileList");
		     go.forward(request,response);
		  }
	  }
%>
</body>
</html>
