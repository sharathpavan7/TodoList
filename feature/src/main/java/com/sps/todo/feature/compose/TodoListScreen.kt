package com.sps.todo.feature.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sps.todo.data.model.Todo
import com.sps.todo.feature.R
import com.sps.todo.feature.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(
    navController: NavHostController,
    viewModel: TodoViewModel
) {
    val todoList by viewModel.todoList.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val errorEvent by viewModel.errorEvent.collectAsState()

    TodoListContent(
        navController = navController,
        todoList = { todoList },
        searchQuery = { searchQuery },
        onSearchChange = { viewModel.setSearchQuery(it) },
        errorEvent = { errorEvent },
        clearErrorEvent = { viewModel.clearErrorEvent() }
    )
}

@Composable
private fun TodoListContent(
    navController: NavHostController,
    todoList: () -> List<Todo>,
    searchQuery: () -> String,
    onSearchChange: (String) -> Unit,
    errorEvent: () -> String?,
    clearErrorEvent: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.todo_list)) },
                backgroundColor = Color(0xFF479B5F)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("todoDetails") },
                backgroundColor = Color(0xFF479B5F)
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_todo))
            }
        },
        content = {
            if (todoList().isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.press_the_button_to_add_a_todo_item))
                }
            } else {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        value = searchQuery(),
                        onValueChange = { onSearchChange(it) },
                        placeholder = { Text(stringResource(R.string.search_todos)) }
                    )

                    errorEvent()?.let {
                        AlertDialog(
                            onDismissRequest = { clearErrorEvent() },
                            title = { Text(stringResource(R.string.error)) },
                            text = { Text(stringResource(R.string.failed_to_add_todo)) },
                            confirmButton = {
                                Button(
                                    colors = ButtonDefaults.buttonColors(Color.Red),
                                    onClick = { clearErrorEvent() }) {
                                    Text(stringResource(R.string.dismiss), color = Color.White)
                                }

                            }
                        )
                    }

                    LazyColumn {
                        items(todoList()) {
                            Text(
                                it.description,
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = false)
private fun TodoListContentPreview() {
    TodoListContent(
        navController = NavHostController(LocalContext.current),
        todoList = { emptyList() },
        searchQuery = { "" },
        onSearchChange = {},
        errorEvent = { "" },
        clearErrorEvent = {}
    )
}