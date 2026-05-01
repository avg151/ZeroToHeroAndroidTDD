package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class ListViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<List<String>> = savedStateHandle.getStateFlow(LIST_TAG, emptyList())

    fun add(text: String) {
        val current = state.value
        savedStateHandle[LIST_TAG] = listOf(text) + current
    }

    companion object {
        private const val LIST_TAG = "list"
    }
}
