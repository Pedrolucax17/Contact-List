package br.com.fiap.contactlist.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.contactlist.model.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDb: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object{
        private lateinit var instance: ContactDb

        fun getDataBase(context: Context): ContactDb{
            if(!::instance.isInitialized){
                instance = Room
                    .databaseBuilder(
                        context,
                        ContactDb::class.java,
                        "contact_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}