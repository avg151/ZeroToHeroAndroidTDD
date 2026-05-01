package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val changeLiveDataWrapper: ListLiveDataWrapper.All,
    private val repository: Repository.Change,
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
                    changeLiveDataWrapper.delete(ItemUi(it.id, it.text))
                }
                clear.clearViewModel(DetailsViewModel::class.java)
            }
        }
    }

    fun update(itemId: Long, newText: String) {
        viewModelScope.launch(dispatcher) {
            repository.update(itemId, newText)
            withContext(dispatcherMain) {
                changeLiveDataWrapper.update(ItemUi(itemId, newText))
                clear.clearViewModel(DetailsViewModel::class.java)
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(DetailsViewModel::class.java)
    }
}
