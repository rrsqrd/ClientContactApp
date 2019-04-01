# Client Contact Maven project
A simple CRUD web application that works with Java 8 and Tomcat 7. 
It uses Spring MVC with JSP views, RestControllers,  Spring JDBC/NamedTemplates, 
DAO objects, and an embedded hsqldb in memory database. 

Allows a user to manage a list of Client(companies) and associated Contacts (persons)

Added ability to manage Clients (i.e companies):
* A user can create, edit, delete and list Clients.
* Clients have a company name, website URI, phone number, and physical/mailing address.
* Clients have zero, one, or multiple associated contacts.
* When editing a contact, the user can choose the associated client.
* When viewing a contact, the associated client is shown.
* When viewing a client, the associated contact(s) are shown.
* When editing a contact, the user is able to add or remove associated contacts.
