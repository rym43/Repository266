package com.example.classtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classtask.ui.theme.ClasstaskTheme

@Composable
fun EventsRoute(
    viewModel: EventsViewModel = viewModel(factory = EventsViewModel.provideFactory())
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    EventsScreen(
        state = uiState,
        onRefresh = { viewModel.refresh() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    state: EventsUiState,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Events") },
                actions = {
                    TextButton(
                        onClick = onRefresh,
                        enabled = state !is EventsUiState.Loading
                    ) {
                        Text("Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is EventsUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is EventsUiState.Content -> {
                    if (state.events.isEmpty()) {
                        Text(
                            text = "No events yet",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = state.events,
                                key = { event -> event.id }
                            ) { event ->
                                EventCard(event)
                            }
                        }
                    }
                }
                is EventsUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Couldn't load events",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRefresh) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: CampusEvent) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = event.startsAt,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = event.location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview() {
    ClasstaskTheme {
        EventsScreen(
            state = EventsUiState.Content(
                listOf(
                    CampusEvent(1, "Welcome Week Fair", "2026-10-21 10:00", "Main Courtyard"),
                    CampusEvent(2, "Hackathon Kickoff", "2026-10-30 18:00", "Innovation Hub")
                )
            ),
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventsScreenErrorPreview() {
    ClasstaskTheme {
        EventsScreen(
            state = EventsUiState.Error("Unable to resolve host"),
            onRefresh = {}
        )
    }
}
