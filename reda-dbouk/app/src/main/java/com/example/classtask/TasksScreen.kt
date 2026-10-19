package com.example.classtask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classtask.ui.theme.ClasstaskTheme

@Composable
fun TasksRoute(
    viewModel: TasksViewModel = viewModel(factory = TasksViewModel.provideFactory())
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    TasksScreen(
        state = uiState,
        onToggle = { task -> viewModel.toggleTask(task) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    state: TasksUiState,
    onToggle: (Task) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is TasksUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TasksUiState.Content -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.tasks,
                            key = { task -> task.id }
                        ) { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = task.isDone,
                                    onCheckedChange = { onToggle(task) }
                                )
                                Text(
                                    text = task.title,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    ClasstaskTheme {
        TasksScreen(
            state = TasksUiState.Content(
                listOf(
                    Task(1L, "Sample Task 1", false),
                    Task(2L, "Sample Task 2", true)
                )
            ),
            onToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenLoadingPreview() {
    ClasstaskTheme {
        TasksScreen(
            state = TasksUiState.Loading,
            onToggle = {}
        )
    }
}
