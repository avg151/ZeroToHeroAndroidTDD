package ru.easycode.zerotoheroandroidtdd.note.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteUi
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository

class CreateNoteViewModel(
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Increment,
    private val addLiveDataWrapper: NoteListLiveDataWrapper.Create,
    private val repository: NotesRepository.Create,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun createNote(folderId: Long, text: String) {
        viewModelScope.launch(dispatcher) {
            val noteId = repository.createNote(folderId, text)
            withContext(dispatcherMain) {
                folderLiveDataWrapper.increment()
                addLiveDataWrapper.create(NoteUi(noteId, text, folderId))
                clear.clear(CreateNoteViewModel::class.java)
                navigation.update(FolderDetailsScreen)
            }
        }
    }

    fun comeback() {
        clear.clear(CreateNoteViewModel::class.java)
        navigation.update(FolderDetailsScreen)
    }
}
