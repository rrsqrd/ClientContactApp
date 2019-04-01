
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta name="viewport" http-equiv="Content-Type" content="text/html, charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <title>Client Contacts</title>
        
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

    	<script>		
		
			$( document ).ready(function() {
				$('#addContact').prop('disabled', true);
			    console.log( "ready, disabled addContact" );
			});
		
	    	/**
	    	  Select Contact name
	    	*/
	        function updateChosenContactId(selectObject) 
	    	{ 
	            var id  = selectObject.value; 		// effectively the pesonId
	            var selectText = $( "#selectContactId option:selected" ).text();
	            
	        	if( id == "0") 
	        	{
	        		alert("You chose (none), invalid contact");
	        		$('#addContact').prop('disabled', true); // disable the button
	        		return false
	        	}
	           
	            $('input[name="contactId"]').val(id);
	            $('#addContact').prop('disabled', false); // enable the button
	            return true;
	  		}
	
        
          /**
           * Note: Function arguments are populated once valid select contact has been chosen.
           *       Hidden field values apply for both Add and Remove contact btns.
           *
           * Contact table holds the foreign key of clientId.
           * Only one client can be assigned to any given contact.
           * But a client(parent) can be associated with many contacts (child).
           *   contact        client
           *   (child)		  (parent)
           *     1       		1
           *     2       		2
           *     3       		1
           *     4       		0
           * 	Add/Assign a client to a contact
           * 	Remove/Unassign a client from a contact
           *
           * Thus the form request will go to contact controller
          */
    	  function handleContactAssignment(contactId, lastName, firstName)
    	  {              
    		var btnAction = document.activeElement.getAttribute('value');  		   	
  		   	var clientId = $('input[name="clientId"]').val();
  		   	 
  		   	if(btnAction === "Remove")
  		   	{   		   		
  			   	// command is a hidden form field passed to Contact controller
  		   	    $('input[name="command"]').val("removeClientFromContact");  		   	    
	   			var urlPath= "${pageContext.request.contextPath}/contact/contact/" + contactId + "/client/" + clientId;  		   			

  		   		$("#addRemoveContactForm").attr("action", urlPath);  		   	    
  		   		$('#addRemoveContactForm').submit();
  		   		return true;
  		   	}
  		   	else if(btnAction === "Add Contact")
  		   	{
  		   		// get contactId having selected a contact name
  	    		var contactId = $('input[name="contactId"]').val()
  	            
  	            $('input[name="command"]').val("addClientToContact");
            
            	if(contactId > 0)
             	{ 
  		   			var urlPath= "${pageContext.request.contextPath}/contact/contact/" + contactId + "/client/" + clientId;  		   			

	  		   		$("#addRemoveContactForm").attr("action", urlPath);
	  		   		$('#addRemoveContactForm').submit();
  		   			return true;
             	}          
  		   	}  		   	
    	  }    	    	    	
    	</script>
    </head>
       
    
    <body style="margin-top:0px; padding-left: 80px;  padding-right: 100px;">
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
           

		<!--  client information -->
	    <div style=margin-top:30px;>	    
        <!--  <strong style="color:blue">Client</strong> -->
		<table style="15px; font-size: 14px;">
	      <tr style="height:20px">
	        <td>
	        	<label>${client.companyName}</label>	        
	        </td>
			<td style="width:50px;"></td>        
	        <td>
	            <label>${client.phoneNumber}</label>	        	        	
	        </td>
	        <td style="width:50px;"></td>
	        <td>        	
				<label>${client.websiteUrl}</label>	        	        	
	        </td>	        
		  </tr>      
	    </table>	    
        <br/>        
        
        <!--  contacts available for assignment to this client -->
        <form id="addRemoveContactForm" name="addRemoveContactForm" method="POST">	            	                            
        <div>
            <input id="clientId" type="hidden" name="clientId" value="${client.clientId}"/>
            <input id="command"  type="hidden" name="command" value=""/>
                        
        	<label class="lbl">Contact Name: &nbsp;&nbsp;</label>			
       		<select class="selectContactId" id="selectContactId" 
        			name="selectContactId"  onchange="updateChosenContactId(this);">

       		    <option value=0>(none)</option>
	            <c:if test="${fn:length(contactsNotAssignedToAnyClient) gt 0}">	         
	                <c:forEach items="${contactsNotAssignedToAnyClient}" var="contact">
	                	<option value="${contact.contactId}">${contact.lastName}&nbsp;&nbsp;${contact.firstName}</option>               	
	                </c:forEach>
	        	</c:if>	        
	         </select>
        	 
             <input class="btn btn-default btn-xs" style="background-color: #D4E6F1;" 			                        
                    id="addContact" type="submit" name="btnAction" value="Add Contact"
                   	onclick='handleContactAssignment("${contact.contactId}", "${contact.lastName}", "${contact.firstName}" );'>
                   	
	         <input id="contactId" type="hidden" name="contactId"/>
			 <br/>       	                                 			 
	    </div>    
        <br/>
        
        <strong style="color:blue">Assigned Contacts</strong>
        <c:choose>
            <c:when test="${fn:length(contactsAssignedToThisClient) gt 0}">
            	<!--  <div class="table-responsive"> -->
            	<div>
                <table id="client" class="clientTable" style="font-size: 10px;">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email Address</th>
                            <th>City</th>
                            <th>State</th>
                            <th>Zip Code</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${contactsAssignedToThisClient}" var="contact">
                            <tr>
                                <td>${contact.firstName}</td>
                                <td>${contact.lastName}</td>
                                <td>${contact.emailAddress}</td>
                                <td>${contact.city}</td>
                                <td>${contact.state}</td>
                                <td>${contact.zipCode}</td>
			                    <td style="width:50px;">
			                        <input class="btn btn-default btn-xs" style="background-color: #D4E6F1; width:60px" 			                        
			                            id="remove" type="text" name="btnAction" value="Remove"
			                            onclick='return handleContactAssignment("${contact.contactId}", "${contact.lastName}", "${contact.firstName}" );'>
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
        </form>   
        </div>        
    </body>
</html>
