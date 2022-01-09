package com.example.ui_room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoItems")
data class ToDo(val Title:String,
                val done:Boolean,
                @PrimaryKey(autoGenerate = false) val id:Int?=null)
