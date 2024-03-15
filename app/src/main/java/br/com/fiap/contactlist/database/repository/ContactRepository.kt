package br.com.fiap.contactlist.database.repository

import android.content.Context
import br.com.fiap.contactlist.database.dao.ContactDb
import br.com.fiap.contactlist.model.Contact

class ContactRepository(context: Context) {

    var db = ContactDb.getDataBase(context).contactDao()

    fun insert(contact: Contact):Long {
        return db.insert(contact)
    }

    fun update(contact: Contact):Int {
        return db.update(contact)
    }

    fun delete(contact: Contact):Int {
        return db.delete(contact)
    }

    fun searchContactId(id: Long):Contact {
        return db.searchContactId(id = id)
    }

    fun listContacts():List<Contact> {
        return db.listContacts()
    }
}