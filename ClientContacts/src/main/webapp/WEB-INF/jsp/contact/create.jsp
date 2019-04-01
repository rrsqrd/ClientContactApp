<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Contact</title>       
    </head>
 
    <style>
		.lbl {
		  float: left;
		  width: 10%;		  
		}
		
		.inptxt {
		  width: 15%;
		}	
	    input:invalid {
	  		border: 1px dashed red;
		}
	
		input:valid {
	  		border: 1px solid black;
		}		
    </style>

    
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script type="text/javascript"></script>
        
    <body style="margin-top:0px; padding-left: 70px; ">
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
        <h2 style="color:blue">Add Contact</h2>
        <c:if test="${fn:length(errors) gt 0}">
            <p>Please correct the following errors in your submission:</p>
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </c:if>
        <form action="${pageContext.request.contextPath}/contact/create" method="POST">
            <br/>
            <div>
            <label class="lbl"  for="firstName">First Name:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="firstName" value="${contact.firstName}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="lastName">Last Name:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="lastName" value="${contact.lastName}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="emailAddress">Email Address:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="emailAddress" value="${contact.emailAddress}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="streetAddress">Street Address:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="streetAddress" value="${contact.streetAddress}" required/>
            </div>
            <br/>
           
            <div> 
            <label class="lbl" for="city">City:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="city" value="${contact.city}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="state">State:</label>
            </div>
            <div>
            <input type="text" name="state" value="${contact.state}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="zipCode">Zip Code:</label>
            </div>
            <div>
            <input type="text" name="zipCode" value="${contact.zipCode}" required/>
            </div>
            <br/>

            <div>
            <label class="lbl">Client</label>
            </div>
            <div>
       		<select id="selectClientId" name="selectClientId" onchange="updateChosenClientId(this);">
       		    <option value=0>(none)</option>
	            <c:if test="${fn:length(clients) gt 0}">	         
	                <c:forEach items="${clients}" var="client">
	                	<option value="${client.clientId}">${client.companyName}</option>
	                </c:forEach>
	        	</c:if>
	         </select>
	         <input id="chosenClientId" type="hidden" name="clientId" value="${contact.clientId}"/>
	         </div>	         
	         <br/>	        
            
            <input type="submit" name="Submit" value="Submit"/>
        </form>
        </div>
    </body>
</html>
