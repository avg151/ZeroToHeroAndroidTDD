package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel(
    private val dao: RecordsDao,
    private val provideTime: ProvideTime,
    private val runAsync: RunAsync
) : ViewModel() {

    val state: StateFlow<List<RecordEntity>> = dao.list()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun add(text: String) {
        if (text.isEmpty()) return
        runAsync.run(viewModelScope) {
            dao.insert(RecordEntity(id = provideTime.now(), text = text))
        }
    }
}
