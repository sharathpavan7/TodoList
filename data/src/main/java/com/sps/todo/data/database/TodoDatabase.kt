package com.sps.todo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sps.todo.data.dao.TodoDao
import com.sps.todo.data.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}