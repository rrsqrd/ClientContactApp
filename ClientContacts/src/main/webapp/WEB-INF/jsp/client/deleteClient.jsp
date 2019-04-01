<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Client</title>        
        
    </head>

    <body style="margin-top:0px; padding-left: 60px; ">
		<table style="15px; font-size: 15px;">
	      <tr style="height:20px"></tr>
	      <tr>        	       
	        <td>
	        	<strong><a style="color:blue" href="${pageContext.request.contextPath}/index">Home</a></strong>	        
	        </td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>        
	        <td>        	
	        	<strong><a  style="color:blue" href="${pageContext.request.contextPath}/client/listClients">Clients</a></strong>        	
	        </td>	  
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
	        <td>        	
	        	<strong><a  style="color:blue" href="${pageContext.request.contextPath}/contact/list">Contacts</a></strong>        	
	        </td>	        
		  </tr>      
	    </table>
    
    	<div style=margin-top:50px;>
        <h2 style="color:blue">Delete Client</h2>       
	    	<c:choose>
	        <c:when test="${fn:length(errors) gt 0}">
		    	<ul>
		        	<c:forEach items="${errors}" var="error">
		            	<li>${error}</li>
		            </c:forEach>
		        </ul>	    
		    </c:when>
	        <c:otherwise>                
	        	<p><strong>You are about to delete client: ${client.companyName} ${client.websiteUrl}:  Are you sure?</strong></p>
	        	<form action="${pageContext.request.contextPath}/client/deleteClient" method="post">
	            <input type="hidden" name="clientId" value="${client.clientId}"/>
	            <input type="submit" name="command" value="Cancel"/>
	            <input type="submit" name="command" value="Delete"/>            	                      
	        	</form>            
	        </c:otherwise>
	        </c:choose>
        </div>        
    </body>
</html>