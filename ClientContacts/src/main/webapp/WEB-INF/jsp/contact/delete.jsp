<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Contact</title>
    </head>
    <body>
        <h1>Delete Contact</h1>
        <p>You are about to delete contact: ${contact.firstName} ${contact.lastName}:  Are you sure?</p>

        <form action="${pageContext.request.contextPath}/contact/delete" method="post">
            <input type="hidden" name="contactId" value="${contact.contactId}"/>
            <input type="submit" name="command" value="Cancel"/>
            <input type="submit" name="command" value="Delete"/>
        </form>
    </body>
</html>
