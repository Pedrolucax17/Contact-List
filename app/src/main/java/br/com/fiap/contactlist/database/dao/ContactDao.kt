package br.com.fiap.contactlist.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.contactlist.model.Contact

@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Contact): Long

    @Update
    fun update(contact: Contact): Int

    @Delete
    fun delete(contact: Contact): Int

    @Query("SELECT * FROM tbl_contact WHERE id = :id")
    fun searchContactId(id: Long): Contact

    @Query("SELECT * FROM tbl_contact ORDER BY name ASC")
    fun listContacts(): List<Contact>
}