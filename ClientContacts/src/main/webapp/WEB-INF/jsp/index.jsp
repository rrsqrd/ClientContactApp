<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
  	  
<head>
	<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Project:	Client Contacts</title>   
</head>

<body>
  <body style="margin-top:50px; padding-left: 100px; ">
    <h1 style="color:blue">Client Contact Management</h1>
    
	<table style="15px; font-size: 15px; margin-top:50px;">
      <tr style="height:20px"></tr>
      <tr>        	       
        <td>
        	<strong><a  style="color:blue" href="${pageContext.request.contextPath}/client/listClients">Show All Clients</a></strong>
        </td>
		<td style="width:130px;"></td>
        <td>
        	<strong><a  style="color:blue" href="${pageContext.request.contextPath}/contact/list">Show All Contacts</a></strong>        	
        </td>
		<td style="width:50px;"></td>
	  </tr>
	  
	  <tr style="height:20px">
	  </tr>
	  <tr style="height:20px">
        <td>
        	<strong><a style="color:blue" href="${pageContext.request.contextPath}/client/createClient">Add New Client</a></strong>
        </td>
		<td style="width:130px;"></td>
        <td>
        	<strong><a style="color:blue" href="${pageContext.request.contextPath}/contact/create">Add New Contact</a></strong>
        </td>	  
	  </tr>
    </table>        
  
 </body>
</html>