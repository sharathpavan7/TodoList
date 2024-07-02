package com.sps.todo.feature.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sps.todo.feature.viewmodel.TodoViewModel

@Composable
fun TodoDetailsScreen(
    navController: NavHostController,
    viewModel: TodoViewModel = hiltViewModel()
) {

    TodoDetailsScreenContent(
        onAddTodoButtonClick = {
            viewModel.addTodo(onComplete = {
                navController.popBackStack()
            })
        },
        todo = { viewModel.todo.value },
        onTodoValueChange = { viewModel.onTodoValueChange(it) },
        addTodoInProgress = { viewModel.addTodoInProgress.value })

}

@Composable
private fun TodoDetailsScreenContent(
    onAddTodoButtonClick: () -> Unit,
    todo: () -> String,
    onTodoValueChange: (String) -> Unit,
    addTodoInProgress: () -> Boolean
) {

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = todo(),
            onValueChange = { onTodoValueChange(it) },
            label = { Text("TODO Item") },
            singleLine = true
        )
        Button(
            onClick = {
                onAddTodoButtonClick()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add TODO")
        }
        if (addTodoInProgress()) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
//        if (/*errorMessage.isNotEmpty()*/ true) {
//            Text("errorMessage", color = Color.Red, modifier = Modifier.padding(top = 16.dp))
//        }
    }

}

@Composable
@Preview
private fun TodoDetailsScreenContentPreview() {
    TodoDetailsScreenContent(
        onAddTodoButtonClick = {},
        todo = { "" },
        onTodoValueChange = {},
        addTodoInProgress = { false })
}