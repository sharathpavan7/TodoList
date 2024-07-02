package com.sps.todo.feature.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sps.todo.data.model.Todo
import com.sps.todo.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _todo = mutableStateOf("")
    val todo: State<String> = _todo

    private val _addTodoInProgress = mutableStateOf(false)
    val addTodoInProgress: State<Boolean> = _addTodoInProgress

    val todoList: StateFlow<List<Todo>> = repository.getTodos()
        .combine(_searchQuery) { todos, query ->
            if (query.isEmpty()) {
                todos
            } else {
                todos.filter { it.description.contains(query) }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    internal fun onTodoValueChange(text: String) {
        _todo.value = text
    }

    internal fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    internal fun addTodo(onComplete: () -> Unit) {
        _addTodoInProgress.value = true
        viewModelScope.launch {
            delay(3000)
            repository.insert(Todo(description = _todo.value))
            _addTodoInProgress.value = false
            onComplete()
        }
    }

}