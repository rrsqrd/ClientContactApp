package com.rrsqrd.clientcontacts.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rrsqrd.clientcontacts.domain.Client;
import com.rrsqrd.clientcontacts.domain.Contact;
import com.rrsqrd.clientcontacts.service.IClientService;
import com.rrsqrd.clientcontacts.service.IContactService;

/**
 * Controller for handling client operations.
 */
@Controller
@RequestMapping("client")
public class ClientController {
    
	public static final String COMMAND_DELETE = "Delete";
	
    @Inject private IClientService clientService;
    @Inject private IContactService contactService;
        
    /**
     * Renders the listing of all contacts assigned to the given client.
     * Returns ModelView object which contains 3 model attributes
     * 	
     * @return list view populated with the current list of contacts
     */
    
    @RequestMapping(value = "clientContacts/{clientId}", method=RequestMethod.GET)
    public ModelAndView list(@PathVariable Integer clientId) 
    {
        ModelAndView mav = new ModelAndView("client/clientContacts"); // view only
                
        //----------------------------------------
        // SELECT DROP BOX:	Contacts NOT assigned to THIS client
        //----------------------------------------
        List<Contact> notAssignedToClient      = getContactsNotAssignedToAnyClient();      
        mav.addObject("contactsNotAssignedToAnyClient", notAssignedToClient);  //Add an attribute to the model.

        //----------------------------------------
        // TABLE:	Contact assigned to THIS SPECIFIC CLIENT
        //----------------------------------------
        List<Contact> assignedToSpecificClient = getContactsAssignedToSpecifiedClient(clientId);        
        mav.addObject("contactsAssignedToThisClient", assignedToSpecificClient); // Add an attribute to the model.       
        
        //-----------------
        // The client
        //-----------------
        //Client tmpClient = clientService.readClient(clientId);        
        //tmpClient.setPhoneNumber(formatPhoneNumForDisplay(tmpClient.getPhoneNumber()) );
        
        mav.addObject("client",  clientService.readClient(clientId)); // Add an attribute to the model.
        //mav.addObject("client",  tmpClient);
        return mav;
    }
        

    /**
     * 
     * @param clientId
     * @return
     */
    public List<Contact> getContactsAssignedToSpecifiedClient(Integer clientId)
    {
    	List<Contact> contacts = new ArrayList<>();
    	contacts = contactService.listContacts();    	

    	for (Iterator<Contact> iterator = contacts.iterator(); iterator.hasNext();)
    	{
    		Contact contact = iterator.next();
    		
    		// if not assigned to this client, remove from list 
    		if(contact.getClientId() != clientId)  
    		{
    		    iterator.remove();
    		}
    	}
    	
    	return contacts;
    }
    
    /**
     * 
     * @return
     */
    public List<Contact> getContactsNotAssignedToAnyClient()
    {
    	List<Contact> contacts = new ArrayList<>();
    	contacts = contactService.listContacts();

    	for (Iterator<Contact> iterator = contacts.iterator(); iterator.hasNext();)
    	{
    		Contact contact = iterator.next();
    		
    		// only want to preserve Contacts with clientId == 0, i.e available.
    		// so exclude contacts having client assignment
    		if(contact.getClientId() > 0)  
    		{
    		    iterator.remove();
    		  }
    	}

    	return contacts;
    }
    
    /**
     * Renders the Client listing page.
     *
     * @return list view populated with the current list of clients
     */
    @RequestMapping(value = "listClients", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("client/listClients");
    	List<Client> clientList = clientService.listClients();        
        mav.addObject("clients", clientList);
        return mav;
    }
    
    /**
     * Renders an empty form used to create a new client record.
     *
     * @return create view populated with an empty client
     */
    @RequestMapping(value = "createClient", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("client/createClient");
        mav.addObject("client", new Client());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }
    
    /**
     * Validates and saves a new client.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param client populated form bean for the client
     * @return redirect, or create view with errors
     */
    @RequestMapping(value = "createClient", method = RequestMethod.POST)
    public ModelAndView create(Client client) {
    	
        List<String> errors = clientService.validateClient(client);
        if (errors.isEmpty()) 
        {
        	// TODO
        	// Add code to check if Client already exists before creating...
        	// Also add it check to update client code..
        	clientService.createClient(client);
            return new ModelAndView("redirect:/client/listClients");
        } else {
            ModelAndView mav = new ModelAndView("client/createClient");
            mav.addObject("client", client);
            mav.addObject("errors", errors);
            return mav;
        }
    }
    

    /**
     * Renders an edit form for an existing client record.
     *
     * @param clientId the ID of the client to edit
     * @return edit view populated from the client record
     */
    @RequestMapping(value = "editClient/{clientId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer clientId) {
        ModelAndView mav = new ModelAndView("client/editClient");
        mav.addObject("client", clientService.readClient(clientId));
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves an edited client.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param client populated form bean for the client
     * @return redirect, or edit view with errors
     */
    @RequestMapping(value = "editClient", method = RequestMethod.POST)
    public ModelAndView edit(Client client) 
    {
        List<String> errors = clientService.validateClient(client);
        if (errors.isEmpty()) {
            clientService.updateClient(client);
            return new ModelAndView("redirect:/client/listClients");
        } else {
            ModelAndView mav = new ModelAndView("client/editClient");
            mav.addObject("client", client);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Renders the deletion confirmation page.
     *
     * @param clientId the ID of the client to be deleted
     * @return delete view populated from the client record
     */
    @RequestMapping(value = "deleteClient/{clientId}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Integer clientId) 
    {    	
        ModelAndView mav = new ModelAndView("client/deleteClient");
		Client client = clientService.readClient(clientId);
		
    	List<Contact> contactsAssignedToThisClient = getContactsAssignedToSpecifiedClient(clientId);
    	Integer numAssignedContacts = contactsAssignedToThisClient.size();
    	if(numAssignedContacts > 0)
    	{    		
    		List<String> errors = new ArrayList<String>();
    		String error = client.getCompanyName() + " currently has " + numAssignedContacts + " contact(s) assigned.";    		
    		errors.add(error);
    		error ="Please remove the client assignment from each contact in order to delete this client.";
    		errors.add(error);
            mav.addObject("errors", errors);            
    	}
    	
        mav.addObject("client", client);
        return mav;
    }

    /**
     * Handles client deletion or cancellation, redirecting to the listing page in either case.
     *
     * @param command the command field from the form
     * @param clientId the ID of the client to be deleted
     * @return redirect to the listing page
     */
    @RequestMapping(value = "deleteClient", method = RequestMethod.POST)
    public String delete(@RequestParam String command, @RequestParam Integer clientId) {    	
		if (COMMAND_DELETE.equals(command)) {
			clientService.deleteClient(clientId);
		}

        return "redirect:/client/listClients";
    }    
    

}
