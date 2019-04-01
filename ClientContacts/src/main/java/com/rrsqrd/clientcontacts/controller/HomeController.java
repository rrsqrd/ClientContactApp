package com.rrsqrd.clientcontacts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Simple controller to redirect to the Client listing page.  
 * Future: add user registration, login, logout, and a landing page.
 * 
 * From inside eclipse, from project select "Run on Server" to start application
 * 		http://localhost:8080/ClientContacts/client/listClients
 */
@Controller
public class HomeController {

    /**
     * Redirect to the clients list.
     * In a production application this could the landing or login page.
     *
     * @return redirect to the Client listings page
     */
    @RequestMapping
    public String index() {    	    	
    	return "redirect:/client/listClients";
    }
}
