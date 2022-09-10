package ru.mts.pgolovko.newsfeed.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mts.pgolovko.newsfeed.data.repositories.NewsFeedRepository
import ru.mts.pgolovko.newsfeed.data.utils.Result
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<NewsFeedUiState>(NewsFeedUiState.Loading)
    val uiState: StateFlow<NewsFeedUiState> = _uiState

    init {
        refresh()
    }

    fun refresh() {
        setUiState(NewsFeedUiState.Loading)
        viewModelScope.launch {
            when (val result = newsFeedRepository.getFeed()) {
                is Result.Success -> setUiState(NewsFeedUiState.Content(feed = result.data))
                is Result.Error -> setUiState(NewsFeedUiState.Error(errorDescription = result.exception.message ?: ""))
            }
        }
    }

    private fun setUiState(newState: NewsFeedUiState) {
        _uiState.value = newState
    }
}