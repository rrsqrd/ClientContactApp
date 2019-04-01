<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Client</title>
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
	  		border: 2px dashed red;
		}
	
		input:valid {
	  		border: 1px solid black;
		}
	</style>

    

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
        <h2 style="color:blue">Edit Client</h2>
        
        <c:if test="${fn:length(errors) gt 0}">
            <p>Please correct the following errors in your submission:</p>
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/client/editClient" method="POST">
        
        	<input id="chosenClientId" type="hidden" name="clientId" value="${client.clientId}"/>
           	<br/>
            <div>
            <label class="lbl"  for="companyName">Company Name:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="companyName" value="${client.companyName}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="webSiteUrl">Website URL:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="websiteUrl" value="${client.websiteUrl}" required/>
            </div>
            <br/>
            
            <div>
            <label id="phone" class="lbl" for="phoneNumber">Phone Number:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="phoneNumber" value="${client.phoneNumber}" required 
            		pattern="1-[0-9]{3}[-][0-9]{3}[-][0-9]{4}" title="1-xxx-xxx-xxxx"/>
            		
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="streetAddress">Street Address:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="streetAddress" value="${client.streetAddress}" required/>
            </div>
            <br/>
           
            <div> 
            <label class="lbl" for="city">City:</label>
            </div>
            <div>
            <input class="inptxt" type="text" name="city" value="${client.city}" required/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="state">State:</label>
            </div>
            <div>
            <input type="text" name="state" value="${client.state}" required pattern="[A-Z]{2}"/>
            </div>
            <br/>
            
            <div>
            <label class="lbl" for="zipCode">Zip Code:</label>
            </div>
            <div>
            <input type="text" name="zipCode" value="${client.zipCode}" required pattern="[0-9]{5}"/>
            </div>
            <br/>
            <input type="submit" name="Submit" value="Update"/>		       	    
        </form>  
        </div>      
    </body>
</html>
