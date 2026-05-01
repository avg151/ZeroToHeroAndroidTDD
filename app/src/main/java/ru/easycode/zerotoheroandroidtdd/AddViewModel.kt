package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun add(value: String) {
        viewModelScope.launch(dispatcher) {
            val id = repository.add(value)
            withContext(dispatcherMain) {
                liveDataWrapper.add(ItemUi(id, value))
                clear.clearViewModel(AddViewModel::class.java)
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(AddViewModel::class.java)
    }
}
