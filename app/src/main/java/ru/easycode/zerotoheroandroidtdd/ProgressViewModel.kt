package ru.easycode.zerotoheroandroidtdd

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.parcelize.Parcelize

class ProgressViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository,
    private val runAsync: RunAsync
) : ViewModel() {

    private val _state = MutableStateFlow<ProgressUi>(
        savedStateHandle.get<ProgressUi>(KEY) ?: ProgressUi.Initial
    )
    val state: StateFlow<ProgressUi> = _state.asStateFlow()

    init {
        if (_state.value == ProgressUi.Loading) {
            loadInternal()
        }
    }

    fun load() {
        _state.value = ProgressUi.Loading
        savedStateHandle.set(KEY, _state.value)
    }

    fun loadInternal() {
        runAsync.runAsync(viewModelScope, {
            repository.load()
        }) { result ->
            val newState = ProgressUi.Data(result)
            _state.value = newState
            savedStateHandle.set(KEY, newState)
        }
    }

    companion object {
        private const val KEY = "ProgressViewModelState"
    }
}

interface Repository {
    suspend fun load(): String
}

interface RunAsync {
    fun <T : Any> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    )
}

sealed interface ProgressUi : Parcelable {

    @Parcelize
    data object Initial : ProgressUi

    @Parcelize
    data object Loading : ProgressUi

    @Parcelize
    data class Data(val value: String) : ProgressUi
}
