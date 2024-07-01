package com.sps.todo.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sps.todo.data.model.Todo
import com.sps.todo.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

//    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())
//    val todoList: StateFlow<List<Todo>> get() = _todoList

    val todoList: Flow<List<Todo>> = repository.getTodos()


    internal fun addTodo(description: String, onComplete: () -> Unit) {
        viewModelScope.launch {
//            delay(3000)
            repository.insert(Todo(description = description))
            onCleared()
        }
    }

}