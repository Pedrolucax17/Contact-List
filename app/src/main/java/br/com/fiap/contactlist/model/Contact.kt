package br.com.fiap.contactlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var phone: String = "",
    @ColumnInfo(name = "is_friend") var isFriend: Boolean = false
)
