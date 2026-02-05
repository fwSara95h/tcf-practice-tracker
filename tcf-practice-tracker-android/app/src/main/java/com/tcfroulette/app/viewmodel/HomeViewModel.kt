package com.tcfroulette.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tcfroulette.app.data.database.TaskEntity
import com.tcfroulette.app.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val task: TaskEntity) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun rollNewPrompt() {
        Log.d(TAG, "rollNewPrompt() called")
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                val task = repository.getRandomTask()
                if (task != null) {
                    Log.d(TAG, "Successfully loaded task #${task.id}")
                    _uiState.value = HomeUiState.Success(task)
                } else {
                    Log.w(TAG, "No prompts available - null returned")
                    _uiState.value = HomeUiState.Error("No prompts available. Database may be empty.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "rollNewPrompt() failed: ${e.message}", e)
                _uiState.value = HomeUiState.Error("Error loading prompt: ${e.message}")
            }
        }
    }

    class Factory(private val repository: TaskRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
