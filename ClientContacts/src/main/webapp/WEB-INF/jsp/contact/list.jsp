<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta name="viewport" http-equiv="content-type" content="text/html, charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
		<title>Contact Listing</title>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>        
    </head>
    
    <!-- 
     created /style.css, file loaded, but page didn't react as if it had, so removed it 
    -->
	<style>
	   	.contactTable {
			border:1px solid #DDE5FF;		
			font-size: 12px;
		}
	
		#contact {
		    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
		    border-collapse: collapse;
		    width: 75%;
		}		
		
		#contact td, #brmk th {
		    border:1px solid #DDE5FF;	    
		    padding: 3px;
		}	
		
		#contact tr:nth-child(even)
			{background-color: #f2f2f2;}
			
		#contact tr:hover 
			{background-color: #ddd;}
	
		#contact th {
		    padding-top: 12px;
		    padding-bottom: 12px;
		    text-align: left;
		    background-color: #E8F8F5;
		    color: black;
		}
	</style>    

    <body style="margin-top:0px; padding-left: 60px;  padding-right: 100px; ">
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
	    <p> <strong><a class="btn btn-default btn-xs"  style="background-color: #D4E6F1;"  
	    				href="${pageContext.request.contextPath}/contact/create">Add New Contact</a></strong></p>
	    <br/>
	    
        <strong style="color:blue">Contact Listings</strong>     

        <c:choose>
            <c:when test="${fn:length(contacts) gt 0}">
            	<div class="table-responsive">
                <table id="contact" class="contactTable" style="font-size: 10px; width: 80%">
                    <thead>
                        <tr>
                            <th> Last Name</th>
                            <th> First Name</th>
                            <th>Email Address</th>
                            <th>Street Address</th>
                            <th>City</th>
                            <th>State</th>
                            <th>ZipCode</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${contacts}" var="contact">
                            <tr>
                                <td style="font-size: 12px;"><strong>${contact.lastName}</strong></td>
                                <td style="font-size: 12px;"><strong> ${contact.firstName}</strong></td>
                                <td>${contact.emailAddress}</td>
                                <td>${contact.streetAddress}</td>
                                <td>${contact.city}</td>
                                <td>${contact.state}</td>
                                <td>${contact.zipCode}</td>

                                <td style="width: 15%;">
                                    <a class="btn btn-default btn-xs" style="height:24px; background-color: #D4E6F1;" href="${pageContext.request.contextPath}/contact/edit/${contact.contactId}">Edit</a>
                                    <a class="btn btn-default btn-xs" style="height:24px; background-color: #D4E6F1;" href="${pageContext.request.contextPath}/contact/delete/${contact.contactId}">Delete</a>
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
