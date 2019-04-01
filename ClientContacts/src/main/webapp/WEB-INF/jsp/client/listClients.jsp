<%@taglib prefix='c'  uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta name="viewport" http-equiv="Content-Type" content="text/html, charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <title>Client Listings</title>
        
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        
	<style>
	   	.clientTable {
			border:1px solid #DDE5FF;		
			font-size: 12px;
	
		}
	
		#client {
		    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
		    border-collapse: collapse;
		    width: 75%;
		}		
		
		#client td, #brmk th {
		    border:1px solid #DDE5FF;	    
		    padding: 3px;
		}
		
		
		#client tr:nth-child(even)
			{background-color: #f2f2f2;}
	
			
		#client tr:hover 
			{background-color: #ddd;}
	
		#client th {
		    padding-top: 12px;
		    padding-bottom: 12px;
		    text-align: left;
		    background-color: #E8F8F5;
		    color: black;
		}
	</style>       
    </head>
    
    <body style="margin-top:0px; padding-left: 60px;  padding-right: 100px;">
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
           

	    <div style=margin-top:60px;>
	    <p> <strong><a class="btn btn-default btn-xs" style="background-color: #D4E6F1;" 
	       href="${pageContext.request.contextPath}/client/createClient">Add New Client</a></strong></p>
	    
	    <br/>

        <strong style="color:blue">Client Listing</strong>
        <br/>
        <c:choose>
            <c:when test="${fn:length(clients) gt 0}"> 
            	<div>
                <table id="client" class="clientTable" style="font-size: 10px; width: 100%">
                    <thead>
                        <tr>
                            <th style="font-size: 12px;"><strong>Company Name</strong></th>
                            <th>Website URL</th>
                            <th>Phone Number</th>
                            <th>Street Address</th>
                            <th>City</th>
                            <th>State</th>
                            <th>ZipCode</th>                            
                            <th>Actions</th>                                                        
                            <th>Contacts</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${clients}" var="client">
                            <tr>
                                <td style="font-size: 12px;"><strong>${client.companyName}</strong></td>
                                <td>${client.websiteUrl}</td>
                                <td>${client.phoneNumber}</td>
                                <td>${client.streetAddress}</td>
                                <td>${client.city}</td>
                                <td>${client.state}</td>
                                <td>${client.zipCode}</td>                                
                                
                                <td>
                                	<a class="btn btn-default btn-xs" style="height:24px; background-color: #D4E6F1;" 
                                			href="${pageContext.request.contextPath}/client/editClient/${client.clientId}">Edit</a>                                                                   
                                    <a class="btn btn-default btn-xs" style="height:24px; background-color: #D4E6F1;" 
                                    		href="${pageContext.request.contextPath}/client/deleteClient/${client.clientId}">Delete</a>                                    
                                </td>
                                <td>
									<a class="btn btn-default btn-xs" style="height:24px; background-color: #D4E6F1;" 
											href="${pageContext.request.contextPath}/client/clientContacts/${client.clientId}">Manage Contacts</a>                                
                                </td>
                            </tr>
                            
                        </c:forEach>
                    </tbody>
                </table>
                </div>
            </c:when>
            <c:otherwise>
                <p>No results found.</p>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>
