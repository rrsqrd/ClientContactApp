package com.rrsqrd.clientcontacts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rrsqrd.clientcontacts.data.dao.IContactDao;
import com.rrsqrd.clientcontacts.domain.Contact;


/** 
 * ContactServiceImpl contains methods to access the respository via IContactDao
 * IContactDao implementation is provided via NamedParameterJdbcTemplate
 *
 * @author Rochelle
 */
public class ContactServiceImpl implements IContactService {

    private IContactDao contactDao;
    private Validator validator;

    public void setContactDao(IContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Contact> listContacts() {
        return contactDao.listContacts();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Contact readContact(Integer id) {
        return contactDao.readContact(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createContact(Contact contact) {
        return contactDao.createContact(contact);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updateContact(Contact contact) {
        contactDao.updateContact(contact);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deleteContact(Integer id) {
        contactDao.deleteContact(id);
    }

    @Override
    public List<String> validateContact(Contact contact) 
    {
    	/*
    	 * Here we are validating each field per Contact model class annotation rules 
    	 */    	
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        List<String> errors = new ArrayList<String>(violations.size());
        for (ConstraintViolation<Contact> violation : violations) {
            errors.add(violation.getMessage());
        }
        
        boolean isEmailValid = true;
        for(String error: errors)
        {
        	if(error.contains("Email address"))
        	{
        		isEmailValid = false;
        		break;
        	}
        }
        
        // don't obscure validate api error
    	if(isEmailValid)
    	{
    		boolean result = validateEmailAddress(contact.getEmailAddress());

    		if(!result)
    		{
    			errors.add("Email address format is invalid");
    		}
    	}
 
        Collections.sort(errors);
        return errors;
    }
    
    @Override
    public boolean doesContactExistByName(String firstName, String lastName)
    {
    	return contactDao.doesContactExistByName(firstName, lastName);
    }
    
	/**
	 * A few test cases
	 * 		armenda.chanucy
	 * 		armenda.chanucy@()*@gmail.com
	 * 		armenda.chanucy@%*.com 
	 * 		armenda.chanucy@@*.com
	 * 		armenda..chanucy@gmail.com
	 * 
	 * @param email
	 * @return boolean
	 */
	public boolean validateEmailAddress(String email) 
    {    
		/**
		 * Java email validation permitted by RFC 5322?? 
		 *  The following regex example uses all the characters permitted by RFC 5322,
		 *  which governs the email message format. 
		 *  Among the permitted characters are some that present a security risk if 
		 *  passed directly from user input to an SQL statement, 
		 *  such as the single quote (‘) and the pipe character (|).
		 * 		^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$
		 * 
		 *  I decided not to use the above string.
		 *  I removed single quote (‘), pipe character {|}, `, ~, ^
		 *  	^[a-zA-Z0-9_!#$%&*+/=?.-]+@[a-zA-Z0-9.-]+$"
		 */

        final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&*+/=?.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);        
        Matcher matcher;
    
		matcher = pattern.matcher(email);
		boolean isMatch = matcher.matches();
		
		return isMatch;
	}
	
}
