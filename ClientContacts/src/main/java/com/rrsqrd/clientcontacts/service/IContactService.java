package com.rrsqrd.clientcontacts.service;

import java.util.List;

import com.rrsqrd.clientcontacts.domain.Contact;

/**
 * Contact operations.
 */
public interface IContactService {

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
     * Validates populated contact data.
     *
     * @param contact the values to validate
     * @return list of error messages
     */
    List<String> validateContact(Contact contact);
    
    /**
     * Check if exists to avoid duplicate contact/records on create
     */
    boolean doesContactExistByName(String firstName, String lastName);
}
