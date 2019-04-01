package com.rrsqrd.clientcontacts.data.dao;

import com.rrsqrd.clientcontacts.domain.Contact;
import java.util.List;

/**
 * Operations on the "contact" table.
 */
public interface IContactDao {

    /**
     * Retrieves all of the contact records.
     *
     * @return list of contact records
     */
    List<Contact> listContacts();
    
    /**
     * Creates a new contact record.
     *
     * @param contact the values to save
     * @return the new contact ID
     */
    Integer createContact(Contact contact);

    /**
     * Retrieves a contact record by ID.
     *
     * @param id the contact ID
     * @return the contact record
     */
    Contact readContact(Integer id);

    /**
     * Updates an existing contact record.
     *
     * @param contact the new values to save
     */
    void updateContact(Contact contact);

    /**
     * Deletes a contact record by ID.
     *
     * @param id the contact ID
     */
    void deleteContact(Integer id);
    
    /**
     * Check if exists to avoid duplicate contact/records on create
     */
    boolean doesContactExistByName(String firstName, String lastName);
    
}
