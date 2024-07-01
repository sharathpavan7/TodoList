package com.sps.todo.feature.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.TodoViewModel


@Composable
fun TodoListScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    TodoListContent()
}


@Composable
fun TodoListContent() {
    Text(
        text = "Hello world",
    )
}


@Composable
@Preview(showBackground = false)
fun TodoListContentPreview() {
    TodoListContent()
}