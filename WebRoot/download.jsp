<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@page import="com.jspsmart.upload.*"%><%
  	SmartUpload smart = new SmartUpload();
  	smart.initialize(pageContext);
  	smart.downloadFile("/"+session.getAttribute("fn"));
%>