package com.rrsqrd.clientcontacts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rrsqrd.clientcontacts.data.dao.IClientDao;
import com.rrsqrd.clientcontacts.domain.Client;

/**
 * ClientServiceImpl contains methods to access the respository via ICleintDao
 * IClientDao implementation is provided via NamedParameterJdbcTemplate
 * 
 * @author Rochelle
 *
 */
public class ClientServiceImpl implements IClientService {
	
    private IClientDao clientDao;
    private Validator  validator;

    public void setClientDao(IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Client> listClients() {
        return clientDao.listClients();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Client readClient(Integer id) {
        return clientDao.readClient(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createClient(Client client) {
        return clientDao.createClient(client);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updateClient(Client client) {
        clientDao.updateClient(client);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deleteClient(Integer id) {
        clientDao.deleteClient(id);
    }

    @Override
    public List<String> validateClient(Client client) 
    {
    	/*
    	 * Here we are validating each field per Client model class annotation rules 
    	 */
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        List<String> errors = new ArrayList<String>(violations.size());
        for (ConstraintViolation<Client> violation : violations) {
            errors.add(violation.getMessage());
        }
        
        
        Collections.sort(errors);
        return errors;
    }	

}
