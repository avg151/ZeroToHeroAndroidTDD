package ru.easycode.zerotoheroandroidtdd.folder.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class CreateFolderViewModel(
    private val repository: FoldersRepository.Create,
    private val liveDataWrapper: FolderListLiveDataWrapper.Create,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun createFolder(name: String) {
        viewModelScope.launch(dispatcher) {
            val id = repository.createFolder(name)
            withContext(dispatcherMain) {
                liveDataWrapper.create(FolderUi(id, name, 0))
                clear.clear(CreateFolderViewModel::class.java)
                navigation.update(FoldersListScreen)
            }
        }
    }

    fun comeback() {
        clear.clear(CreateFolderViewModel::class.java)
        navigation.update(FoldersListScreen)
    }
}
