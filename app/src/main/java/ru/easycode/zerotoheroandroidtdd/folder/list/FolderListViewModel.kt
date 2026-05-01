package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.create.CreateFolderScreen
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class FolderListViewModel(
    private val repository: FoldersRepository.ReadList,
    private val listLiveDataWrapper: FolderListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Update,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun init() {
        viewModelScope.launch(dispatcher) {
            val folders = repository.folders()
            val foldersUi = folders.map { FolderUi(it.id, it.title, it.notesCount) }
            withContext(dispatcherMain) {
                listLiveDataWrapper.update(foldersUi)
            }
        }
    }

    fun addFolder() {
        navigation.update(CreateFolderScreen)
    }

    fun folderDetails(folderUi: FolderUi) {
        folderLiveDataWrapper.update(folderUi)
        navigation.update(FolderDetailsScreen)
    }
}
