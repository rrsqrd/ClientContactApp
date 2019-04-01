package com.rrsqrd.clientcontacts.data.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rrsqrd.clientcontacts.data.dao.IClientDao;
import com.rrsqrd.clientcontacts.domain.Client;


/**
 * Queries with Named Parameters
 * To get support for named parameters, use JDBC template provided by the framework – 
 * which is NamedParameterJdbcTemplate.
 * 
 * Additionally, this wraps the JbdcTemplate and provides an alternative to the 
 * traditional syntax using “?” to specify parameters. 
 * Under the hood, it substitutes the named parameters to JDBC “?” placeholder 
 * and delegates to the wrapped JDCTemplate to execute the queries:
 *
 */
public class ClientJdbcDao implements IClientDao{
	
    private static final String SQL_LIST_CLIENT   = "SELECT * FROM client ORDER BY company_name, client_id";
    private static final String SQL_READ_CLIENT   = "SELECT * FROM client WHERE client_id = :clientId";
    private static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE client_id = :clientId";
    
    private static final String SQL_UPDATE_CLIENT = "UPDATE client SET (company_name, website_url, phone_number, street_address, city, state, zip_code)"
                                                  + " = (:companyName, :websiteUrl, :phoneNumber, :streetAddress, :city, :state, :zipCode)"
                                                  + " WHERE client_id = :clientId";
    
    private static final String SQL_CREATE_CLIENT = "INSERT INTO client (company_name, website_url, phone_number, street_address, city, state, zip_code)"
                                                  + " VALUES (:companyName, :websiteUrl, :phoneNumber, :streetAddress, :city, :state, :zipCode)";
      
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * listClients
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Client> listClients() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(SQL_LIST_CLIENT, new ClientRowMapper());
    }
    
    /**
     * ReadClient
     */    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Client readClient(Integer clientId) {
        return namedParameterJdbcTemplate.queryForObject(SQL_READ_CLIENT, Collections.singletonMap("clientId", clientId), new ClientRowMapper());
    }

    /**
     * deleteClient
     */    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deleteClient(Integer clientId) 
    {
        namedParameterJdbcTemplate.update(SQL_DELETE_CLIENT, Collections.singletonMap("clientId", clientId));        
    }

    /**
     * updateClient
     */    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updateClient(Client client) {
        namedParameterJdbcTemplate.update(SQL_UPDATE_CLIENT, new BeanPropertySqlParameterSource(client));
    }

    /**
     * createClient
     */    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createClient(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_CLIENT, new BeanPropertySqlParameterSource(client), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Row mapper for client records.
     * Here we map query results to Java objects via implementation of 
     * RowMapper interface for Client class
     * For every row returned by a query, Spring uses the row mapper to populate the java bean:
     */
    private static final class ClientRowMapper implements RowMapper<Client> 
    {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
        	Client client = new Client();
            client.setClientId(rs.getInt("client_id"));
            client.setCompanyName(rs.getString("company_name"));

            client.setWebsiteUrl(rs.getString("website_url"));
            client.setPhoneNumber(rs.getString("phone_number"));
            
            client.setStreetAddress(rs.getString("street_address"));
            client.setCity(rs.getString("city"));
            client.setState(rs.getString("state"));
            client.setZipCode(rs.getString("zip_code"));
            return client;
        }
    }

}
