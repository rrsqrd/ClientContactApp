package com.rrsqrd.clientcontacts.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  Hibernate Validator is the reference implementation of the validation API
 *  The contact entity corresponding to the "contact" table in the database.
 */
public class Contact {

    private Integer contactId;    

    @NotNull
    @Size(min = 1, max = 50, message = "First name is required with maximum length of 50")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50, message = "Last name is required with maximum length of 50")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 50, message = "Email address is required with maximum length of 50")
    private String emailAddress;

    @NotNull
    @Size(min = 1, max = 50, message = "Street address is required with maximum length of 50")
    private String streetAddress;

    @NotNull
    @Size(min = 1, max = 50, message = "City is required with maximum length of 50")
    private String city;

    @NotNull
    @Size(min = 2, max = 2, message = "State is required with length 2")
    private String state;

    @NotNull
    @Size(min = 5, max = 5, message = "Zip code is required with length 5")
    private String zipCode;

    // foreign key in contact db table, reference to given client
    // Client/companies can be associated with multiple contacts
    // Contact only has 1 association with a client
    private Integer clientId = null; 
    
    public Integer getContactId() { return contactId; }
    public void setContactId(Integer contactId) { this.contactId = contactId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {  return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // If time allows, consider making a unique class for Address info
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    
	@Override
	public String toString() {
		return "\nContact { contactId: " + contactId + 
						", firstName: " + firstName + 
						", lastName: " + lastName +
						", emailAddress: " + emailAddress +
						", streetAddress: " + streetAddress +
						", city: " + city +
						", state: " + state +				
						", zipCode=" + zipCode +
						", clientId=" + clientId +
						'}';
	}
}
