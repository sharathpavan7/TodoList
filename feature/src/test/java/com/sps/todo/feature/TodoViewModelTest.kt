package com.sps.todo.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sps.todo.data.model.Todo
import com.sps.todo.data.repository.TodoRepository
import com.sps.todo.feature.viewmodel.TodoViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class TodoViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TodoViewModel
    private val repository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        coEvery { repository.getTodos() } returns flowOf(emptyList())
        viewModel = TodoViewModel(repository)
    }

    @Test
    fun testInitialState() {
        assertThat(viewModel.todo.value).isEmpty()
        assertThat(viewModel.searchQuery.value).isEmpty()
        assertThat(viewModel.errorEvent.value).isNull()
        assertThat(viewModel.isTodoError.value).isFalse()
        assertThat(viewModel.addTodoInProgress.value).isFalse()
    }

    @Test
    fun testOnTodoValueChange() {
        viewModel.onTodoValueChange("New Todo")
        assertThat(viewModel.todo.value).isEqualTo("New Todo")
    }

    @Test
    fun testClearErrorEvent() {
        viewModel.clearErrorEvent()
        assertThat(viewModel.errorEvent.value).isNull()
    }

    @Test
    fun testSetSearchQuery() {
        viewModel.setSearchQuery("Search Query")
        assertThat(viewModel.searchQuery.value).isEqualTo("Search Query")
    }

    @Test
    fun testAddTodoValidationFails() {
        viewModel.addTodo {}
        assertThat(viewModel.isTodoError.value).isTrue()
    }

    @Test
    fun testAddTodoSuccess() = runTest {
        val todoDescription = "New Todo"
        coEvery { repository.insert(any()) } just Runs

        viewModel.onTodoValueChange(todoDescription)
        viewModel.addTodo {}

        // Ensure the coroutine runs
        advanceUntilIdle()

        coVerify { repository.insert(Todo(description = todoDescription)) }
        assertThat(viewModel.addTodoInProgress.value).isFalse()
    }

    @Test
    fun testAddTodoError() = runTest {
        viewModel.onTodoValueChange("Error")
        viewModel.addTodo {}
        advanceUntilIdle()

        assertThat(viewModel.errorEvent.value).isEqualTo("Failed to add TODO")
        assertThat(viewModel.addTodoInProgress.value).isFalse()
    }
}