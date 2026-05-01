package ru.easycode.zerotoheroandroidtdd.folder.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsViewModel
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class EditFolderViewModel(
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Rename,
    private val repository: FoldersRepository.Edit,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun renameFolder(folderId: Long, newName: String) {
        viewModelScope.launch(dispatcher) {
            repository.rename(folderId, newName)
            withContext(dispatcherMain) {
                folderLiveDataWrapper.rename(newName)
                clear.clear(EditFolderViewModel::class.java)
                navigation.update(FolderDetailsScreen)
            }
        }
    }

    fun deleteFolder(folderId: Long) {
        viewModelScope.launch(dispatcher) {
            repository.delete(folderId)
            withContext(dispatcherMain) {
                clear.clear(EditFolderViewModel::class.java, FolderDetailsViewModel::class.java)
                navigation.update(FoldersListScreen)
            }
        }
    }

    fun comeback() {
        clear.clear(EditFolderViewModel::class.java)
        navigation.update(FolderDetailsScreen)
    }
}
