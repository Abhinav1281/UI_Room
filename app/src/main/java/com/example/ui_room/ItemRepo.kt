package com.example.ui_room

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.to_do.utils.subscribeOnBackground

class ItemRepo(app:Application) {

    private var dao:DateAccessObject
    private var allItems:LiveData<List<ToDo>>

    private val database=Database.getInstance(app)

    init {
        dao=database.dao()
        allItems=dao.getAllItems()
    }

    fun insert(item:ToDo)
    {
        subscribeOnBackground { dao.insert(item)}
    }

    fun update(item: ToDo)
    {
        subscribeOnBackground { dao.update(item)}
    }

    fun delete(item: ToDo)
    {
        subscribeOnBackground { dao.delete(item)}
    }

    fun deleteAllItems()
    {
        subscribeOnBackground { dao.deleteAllItems()}
    }

    fun getAllItems():LiveData<List<ToDo>>
    {
        return allItems
    }
}