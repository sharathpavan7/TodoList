package com.sps.todo.data.repository

import com.sps.todo.data.dao.TodoDao
import com.sps.todo.data.model.Todo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor(private val todoDao: TodoDao) {
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    fun getTodos(): Flow<List<Todo>> = todoDao.getTodos()
}