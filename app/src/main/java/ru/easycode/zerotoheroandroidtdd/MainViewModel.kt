package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class UiState {
    object ShowProgress : UiState()
    object ShowData : UiState()
}

interface Repository {
    suspend fun load()
}

class MainRepository : Repository {
    override suspend fun load() {
        delay(3000)
    }
}

interface LiveDataWrapper {
    fun update(value: UiState)
    fun liveData(): LiveData<UiState>
}

class MainLiveDataWrapper : LiveDataWrapper {
    private val liveData = MutableLiveData<UiState>()
    override fun update(value: UiState) {
        liveData.value = value
    }
    override fun liveData(): LiveData<UiState> = liveData
}

class MainViewModel(
    private val repository: Repository,
    private val liveDataWrapper: LiveDataWrapper
) : ViewModel() {

    fun load() {
        viewModelScope.launch {
            liveDataWrapper.update(UiState.ShowProgress)
            repository.load()
            liveDataWrapper.update(UiState.ShowData)
        }
    }

    fun liveData(): LiveData<UiState> = liveDataWrapper.liveData()
}
