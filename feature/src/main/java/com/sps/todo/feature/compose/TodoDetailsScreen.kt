package com.todo.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    TodoDetailsScreenContent(onAddTodoButtonClick = { description ->
        viewModel.addTodo(description, onComplete = {
            navController.popBackStack()
        })
    })

}

@Composable
private fun TodoDetailsScreenContent(onAddTodoButtonClick: (String) -> Unit) {

    var text by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
//    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("TODO Item") },
            singleLine = true
        )
        Button(
            onClick = {
                onAddTodoButtonClick(text)
                if (/*text == "Error"*/ true) {
//                    errorMessage = "Failed to add TODO"
//                    viewModel.onBackToList()
                } else {
//                    isLoading = true
//                    viewModel.addTodo(text) {
//                        isLoading = false
//                        viewModel.onBackToList()
//                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add TODO")
        }
        if (true) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
        if (/*errorMessage.isNotEmpty()*/ true) {
            Text("errorMessage", color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        }
    }

}

@Composable
@Preview
private fun TodoDetailsScreenContentPreview() {
    TodoDetailsScreenContent(onAddTodoButtonClick = {})
}