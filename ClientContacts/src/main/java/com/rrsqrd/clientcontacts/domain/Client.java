package com.rrsqrd.clientcontacts.domain;

/**
 *  Hibernate Validator is the reference implementation of the validation API
 *  
 */

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Client 
{	
	private Integer clientId;

    @NotNull
    @Size(min = 1, max = 50, message = "Company name is required with maximum length of 50")
    private String companyName;

    @NotNull
    @Size(min = 1, max = 50, message = "Website URL is required with maximum length of 50")
    private String websiteUrl;    
    
    @NotNull
    @Size(min = 1, max = 20, message = "Phone Number is required, format xxx-xxx-xxx")
    private String phoneNumber;
    
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
    

	/*------------------------------------*/    
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	@Override
	public String toString()
	{
		return "\nClient {" +
				"clientId=" + clientId +
				", companyName=" + companyName +
				", websiteUrl=" + websiteUrl +
				", phoneNumber=" + phoneNumber +
				", streetAddress: " + streetAddress +
				", city: " + city +
				", state: " + state +				
				", zipCode=" + zipCode +
				'}';		
	}

}
