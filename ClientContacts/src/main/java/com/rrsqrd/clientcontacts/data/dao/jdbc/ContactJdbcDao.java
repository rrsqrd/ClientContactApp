package com.rrsqrd.clientcontacts.data.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rrsqrd.clientcontacts.data.dao.IContactDao;
import com.rrsqrd.clientcontacts.domain.Contact;

/**
 * Spring JDBC implementation of {@link IContactDao}.
 */
public class ContactJdbcDao implements IContactDao {

    private static final String SQL_LIST_CONTACTS  = "SELECT * FROM contact ORDER BY first_name, last_name, contact_id";
    private static final String SQL_READ_CONTACT   = "SELECT * FROM contact WHERE contact_id = :contactId";
    private static final String SQL_DELETE_CONTACT = "DELETE FROM contact WHERE contact_id = :contactId";
    
    private static final String SQL_UPDATE_CONTACT_WITH_CLIENT = "UPDATE contact SET (first_name, last_name, email_address, street_address, city, state, zip_code, client_id)"
            									  + " = (:firstName, :lastName, :emailAddress, :streetAddress, :city, :state, :zipCode, :clientId)"
                                                  + " WHERE contact_id = :contactId";
    
    private static final String SQL_CREATE_CONTACT = "INSERT INTO contact (first_name, last_name, email_address, street_address, city, state, zip_code)"
                                                  + " VALUES (:firstName, :lastName, :emailAddress, :streetAddress, :city, :state, :zipCode)";

    private static final String SQL_CREATE_CONTACT_WITH_CLIENT = "INSERT INTO contact (first_name, last_name, email_address, street_address, city, state, zip_code, client_id)"
            										+ " VALUES (:firstName, :lastName, :emailAddress, :streetAddress, :city, :state, :zipCode, :clientId)";   
    
    private static final String SQL_FIND_CONTACT_BY_FIRST_LAST_NAME = "SELECT * FROM contact WHERE first_name = :first_name AND last_name = :last_name";
     
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * 
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Contact> listContacts() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(SQL_LIST_CONTACTS, 
        															new ContactRowMapper());
    }

    /**
     * 
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Contact readContact(Integer contactId) {
    	Contact contact = namedParameterJdbcTemplate.queryForObject(SQL_READ_CONTACT, 
    													Collections.singletonMap("contactId", contactId), 
    													new ContactRowMapper());
    	return contact;
    }

    /**
     * 
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deleteContact(Integer contactId) {
        namedParameterJdbcTemplate.update(SQL_DELETE_CONTACT, 
        			Collections.singletonMap("contactId", contactId));
    }

    /**
     * 
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updateContact(Contact contact) {
    	namedParameterJdbcTemplate.update(SQL_UPDATE_CONTACT_WITH_CLIENT, 
    					new BeanPropertySqlParameterSource(contact));
    }

    /**
     * 
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createContact(Contact contact) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Integer clientId=contact.getClientId();       
        
        if(clientId==null)
        {
        	namedParameterJdbcTemplate.update(SQL_CREATE_CONTACT, 
        									  new BeanPropertySqlParameterSource(contact), 
        									  keyHolder);        	
        }
        else
        {
        	namedParameterJdbcTemplate.update(SQL_CREATE_CONTACT_WITH_CLIENT, 
        										new BeanPropertySqlParameterSource(contact), 
        										keyHolder);        	
        }        
        return keyHolder.getKey().intValue();
    }
    
    /**
     *   
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean doesContactExistByName(String firstName, String lastName)
    {
    	// Using hashMap for params since Collections.singleton only holds one key/value pair...
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("first_name", firstName);
        paramMap.put("last_name", lastName);
        
        try 
        {
        	// -namedParameterJdbcTemplate.queryForObject() throws InvalidDataAccessApiUsageException
        	//  if record does not exist this is because method expects at least 1 resultSet.
        	// -Workaround was to use use namedParameterJdbcTemplate.query() but it returns a list... 
        	List<Contact> contacts = namedParameterJdbcTemplate.query(SQL_FIND_CONTACT_BY_FIRST_LAST_NAME,
        															  paramMap,
	    															  new ContactRowMapper());
        	
	    	if(contacts != null) { 
	    		if(contacts.size() == 1){	    			
	    			return true;
	    		}
	    		else if(contacts.size() == 0) {
	    			return false;
	    		}
	    	}
        }
        catch(Exception e)
        {
        	// gracefully handle org.springframework.dao.InvalidDataAccessApiUsageException:
        	System.out.println("Exception: " + e.getMessage());
        }
    	    	
    	return false; 
    }

    /**
     * Row mapper for contact records.
     */
    private static final class ContactRowMapper implements RowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setContactId(rs.getInt("contact_id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setEmailAddress(rs.getString("email_address"));
            contact.setStreetAddress(rs.getString("street_address"));
            contact.setCity(rs.getString("city"));
            contact.setState(rs.getString("state"));
            contact.setZipCode(rs.getString("zip_code"));
            contact.setClientId(rs.getInt("client_id"));                        
            return contact;
        }
    }

}
