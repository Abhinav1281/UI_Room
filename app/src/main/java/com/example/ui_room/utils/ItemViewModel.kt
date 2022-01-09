package com.example.ui_room.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ui_room.ItemRepo
import com.example.ui_room.ToDo

class ItemViewModel(app: Application): AndroidViewModel(app) {

    private val repository= ItemRepo(app)
    private val allItems=repository.getAllItems()

    fun insert(item: ToDo)
    {
        repository.insert(item)
    }

    fun update(item: ToDo)
    {
        repository.update(item)
    }

    fun delete(item: ToDo)
    {
        repository.delete(item)
    }

    fun deleteAllItems()
    {
        repository.deleteAllItems()
    }

    fun getAllItems(): LiveData<List<ToDo>>
    {
        return allItems
    }

}