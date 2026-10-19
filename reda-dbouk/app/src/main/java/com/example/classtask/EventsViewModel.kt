package com.example.classtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = EventsUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                EventsUiState.Content(repository.getEvents())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                EventsUiState.Error(e.message ?: "Could not load events")
            }
        }
    }

    companion object {
        fun provideFactory(repository: EventsRepository = EventsRepository()): androidx.lifecycle.ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    EventsViewModel(repository)
                }
            }
    }
}
