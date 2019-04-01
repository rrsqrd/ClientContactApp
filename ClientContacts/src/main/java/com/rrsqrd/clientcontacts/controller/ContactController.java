package com.rrsqrd.clientcontacts.controller;

import java.util.ArrayList;
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

import com.rrsqrd.clientcontacts.service.IContactService;
import com.rrsqrd.clientcontacts.service.IClientService;



/**
 * Controller for handling Contact operations
 */
@Controller
@RequestMapping("contact")
public class ContactController {

    public static final String COMMAND_DELETE = "Delete";
	public static final String COMMAND_ASSIGN_CLIENT   = "addClientToContact";
	public static final String COMMAND_UNASSIGN_CLIENT = "removeClientFromContact";

    @Inject private IContactService contactService;
    @Inject private IClientService clientService;
    
    /*
     *  Update the client association for a specific contact
     */    
    @RequestMapping(value = "contact/{contactId}/client/{clientId}", method=RequestMethod.POST)    
    public String contactClientAssociation(@RequestParam String  command, 
    						     		   @PathVariable Integer contactId,
    						    		   @PathVariable Integer clientId)                                 
    {        
        // Use existing updateClient method for assign/unassign.
        // Update requires all the current property values in contact object.        
        Contact contact = contactService.readContact(contactId);        
        
        if (COMMAND_ASSIGN_CLIENT.equals(command)) 
        {	        
	        contact.setClientId(clientId);	        
	        contactService.updateContact(contact);
        }        
        else if  (COMMAND_UNASSIGN_CLIENT.equals(command)) {
        	// table primary key starts at 1, so 0 is invalid, 
        	// contact table allows null on clientId fk column
	        contact.setClientId(null);  // 12:30pm this worked for unassignment 
	        contactService.updateContact(contact);
        }
        
        return ("redirect:/client/clientContacts/" + clientId);
    }
    
    /**
     *  List of clients used in create & edit page select list population
     * @return
     */
    public List<Client> getClientList() {
        List<Client> clList = new ArrayList<>();
        clList = clientService.listClients();        
        return clList;
    }
    

    /**
     * Renders the listing page.
     *
     * @return list view populated with the current list of contacts
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("contact/list");        
        mav.addObject("contacts", contactService.listContacts());
        return mav;
    }
    
    /**
     * Renders an empty form used to create a new contact record.
     *
     * @return create view populated with an empty contact
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("contact/create");
        mav.addObject("contact", new Contact());
        mav.addObject("errors", new ArrayList<String>());
        mav.addObject("clients", getClientList());
        return mav;
    }

    
    /**
     * Validates and saves a new contact.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param contact populated form bean 
     * @return redirect, or create view with errors
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView create(Contact contact) {    	    	

        List<String> errors = contactService.validateContact(contact);
        if (errors.isEmpty()) 
        {
        	// Ensure Contact does not already exist
        	// TODO: Also add it to update contact code..
        	boolean contactExists = contactService.doesContactExistByName(contact.getFirstName(), contact.getLastName());
        	
        	if(contactExists)	{        		
        		errors.add("A contact with specified first and last name already exists");
        	}
        	else {        	
        		contactService.createContact(contact);
        		return new ModelAndView("redirect:/contact/list");
        	}          
        }

        ModelAndView mav = new ModelAndView("contact/create");
        mav.addObject("contact", contact);
        mav.addObject("errors", errors);
        return mav;        
    }

    /**
     * Renders an edit form for an existing contact record.
     *
     * @param contactId the ID of the contact to edit
     * @return edit view populated from the contact record
     */
    @RequestMapping(value = "edit/{contactId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer contactId) {    	
        ModelAndView mav = new ModelAndView("contact/edit");
        mav.addObject("contact", contactService.readContact(contactId));
        mav.addObject("errors", new ArrayList<String>());
        mav.addObject("clients", getClientList());
        return mav;
    }

    /**
     * Validates and saves an edited contact.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param contact populated form bean for the contact
     * @return redirect, or edit view with errors
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(Contact contact) {
        List<String> errors = contactService.validateContact(contact);        
        if (errors.isEmpty()) 
        {
        	if(contact.getClientId() == 0)
        	{
        		//new clientId value 0, change to null as client is being removed 
        		contact.setClientId(null);
        	}
        	
        	contactService.updateContact(contact);
            return new ModelAndView("redirect:/contact/list");
        } else {
            ModelAndView mav = new ModelAndView("contact/edit");
            mav.addObject("contact", contact);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Renders the deletion confirmation page.
     *
     * @param contactId the ID of the contact to be deleted
     * @return delete view populated from the contact record
     */
    @RequestMapping(value = "delete/{contactId}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Integer contactId) {
        ModelAndView mav = new ModelAndView("contact/delete");
        mav.addObject("contact", contactService.readContact(contactId));
        return mav;
    }

    /**
     * Handles contact deletion or cancellation, redirecting to the listing page in either case.
     *
     * @param command the command field from the form
     * @param contactId the ID of the contact to be deleted
     * @return redirect to the listing page
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam String command, @RequestParam Integer contactId) {
        if (COMMAND_DELETE.equals(command)) {
        	contactService.deleteContact(contactId);
        }
        return "redirect:/contact/list";
    }
}
