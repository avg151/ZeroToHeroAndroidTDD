package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteViewModel(
    private val deleteLiveDataWrapper: ListLiveDataWrapper.All,
    private val repository: Repository.Delete,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    private var item: Item? = null

    fun init(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            val result = repository.item(itemId)
            item = result
            withContext(dispatcherMain) {
                _liveData.value = result.text
            }
        }
    }

    fun delete(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            repository.delete(itemId)
            withContext(dispatcherMain) {
                item?.let {
                    deleteLiveDataWrapper.delete(ItemUi(it.id, it.text))
                }
                clear.clearViewModel(DeleteViewModel::class.java)
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(DeleteViewModel::class.java)
    }
}
