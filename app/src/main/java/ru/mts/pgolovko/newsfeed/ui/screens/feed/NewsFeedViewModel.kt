package ru.mts.pgolovko.newsfeed.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mts.pgolovko.newsfeed.model.FeedItem
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NewsFeedViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow<NewsFeedUiState>(NewsFeedUiState.Loading)
    val uiState: StateFlow<NewsFeedUiState> = _uiState

    init {
        refresh()
    }

    fun refresh() {
        setUiState(NewsFeedUiState.Loading)
        viewModelScope.launch {

            delay(3000)
            if (Random.nextInt() % 3 == 0) {
                setUiState(NewsFeedUiState.Error("No internet connection. Connect your phone to internet and try again"))
            } else {
                val feed = listOf(
                    FeedItem(
                        id = 1,
                        title = "8 причин не покупать iPhone 14",
                        description = "Apple представила новое поколение iPhone. Базовая модель всегда была самой популярной в линейке: инновации по неизменной цене,..."
                    ),
                    FeedItem(
                        id = 2,
                        title = "5 любопытных моментов, которые надо знать про новые iPhone 14 и iPhone 14 Pro",
                        description = "Apple представила новые iPhone 14 и iPhone 14 Pro. Обновление получилось достаточно сочным. Есть большой простор для дискуссии."
                    ),
                    FeedItem(
                        id = 3,
                        title = "Россияне чаще выбирают цвет Deep Purple при заказе iPhone 14 - Мировые новости",
                        description = "Россияне чаще выбирают цвет Deep Purple при заказе нового смартфона iPhone 14. Об этом сообщает ТАСС со ссылкой на данные ритейлеров."
                    ),
                )

                setUiState(NewsFeedUiState.Content(feed = feed))
            }
        }
    }

    private fun setUiState(newState: NewsFeedUiState) {
        _uiState.value = newState
    }
}