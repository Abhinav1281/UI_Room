package com.example.ui_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.to_do.utils.subscribeOnBackground

@Database(entities = [ToDo::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun dao():DateAccessObject

    companion object
    {
        private var instance:com.example.ui_room.Database?=null

        @Synchronized
        fun getInstance(ctx: Context) : com.example.ui_room.Database
        {
            if(instance==null)
                instance= Room.databaseBuilder(ctx.applicationContext,com.example.ui_room.Database::class.java
                    ,"todo_database").fallbackToDestructiveMigration().addCallback(roomCallback).build()
            return instance!!
        }

        private val roomCallback=object:Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDB(instance!!)
            }
        }

        private fun populateDB(db:com.example.ui_room.Database)
        {
            val listDao=db.dao()
            subscribeOnBackground {
                listDao.insert(ToDo("Example_1",true))
                listDao.insert(ToDo("Example_2",false))
            }
        }
    }
}