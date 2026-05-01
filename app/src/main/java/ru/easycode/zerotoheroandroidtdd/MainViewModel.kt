package ru.easycode.zerotoheroandroidtdd

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.parcelize.Parcelize

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository,
    private val runAsync: RunAsync,
    private val connection: MonitorConnection
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<ProgressUi>(
        savedStateHandle.get<ProgressUi>(KEY) ?: ProgressUi.Empty
    )
    val stateFlow: StateFlow<ProgressUi> = _stateFlow.asStateFlow()

    private var alreadyConnected: Boolean = false

    init {
        runAsync.runFlow(viewModelScope, connection.connectedFlow()) { connected ->
            if (_stateFlow.value == ProgressUi.Empty) {
                initial(connected)
            } else {
                update(alreadyConnected, connected)
            }
            alreadyConnected = connected
        }
        if (_stateFlow.value == ProgressUi.Loading) {
            loadInternal()
        }
    }

    fun initial(connected: Boolean) {
        _stateFlow.value = if (connected) ProgressUi.Connected else ProgressUi.Disconnected
        savedStateHandle.set(KEY, _stateFlow.value)
    }

    fun update(alreadyConnected: Boolean, connected: Boolean) {
        if (_stateFlow.value is ProgressUi.Loading || _stateFlow.value is ProgressUi.Data) return
        if (alreadyConnected != connected) {
            _stateFlow.value = if (connected) ProgressUi.Connected else ProgressUi.Disconnected
            savedStateHandle.set(KEY, _stateFlow.value)
        }
    }

    fun load() {
        _stateFlow.value = ProgressUi.Loading
        savedStateHandle.set(KEY, _stateFlow.value)
    }

    fun loadInternal() {
        runAsync.runAsync(viewModelScope, {
            repository.load()
        }) { result ->
            val newState = ProgressUi.Data(result)
            _stateFlow.value = newState
            savedStateHandle.set(KEY, newState)
        }
    }

    companion object {
        private const val KEY = "MainViewModelState"
    }
}

interface MonitorConnection {
    fun connectedFlow(): Flow<Boolean>
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

    fun <T : Any> runFlow(
        scope: CoroutineScope,
        flow: Flow<T>,
        onEach: (T) -> Unit
    )
}

sealed interface ProgressUi : Parcelable {

    @Parcelize
    data object Empty : ProgressUi

    @Parcelize
    data object Disconnected : ProgressUi

    @Parcelize
    data object Connected : ProgressUi

    @Parcelize
    data object Loading : ProgressUi

    @Parcelize
    data class Data(val value: String) : ProgressUi
}
