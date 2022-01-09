package com.example.ui_room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DateAccessObject {
    @Insert
    fun insert(item:ToDo)

    @Update
    fun update(item:ToDo)

    @Delete
    fun delete(item:ToDo)

    @Query("Delete from ToDoItems")
    fun deleteAllItems()

    @Query("select * from ToDoItems")
    fun getAllItems():LiveData<List<ToDo>>
}