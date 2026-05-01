package ru.easycode.zerotoheroandroidtdd.note.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository

class EditNoteViewModel(
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Decrement,
    private val noteLiveDataWrapper: NoteLiveDataWrapper,
    private val noteListLiveDataWrapper: NoteListLiveDataWrapper.Update,
    private val repository: NotesRepository.Edit,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun init(noteId: Long) {
        viewModelScope.launch(dispatcher) {
            val note = repository.note(noteId)
            withContext(dispatcherMain) {
                noteLiveDataWrapper.update(note.title)
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch(dispatcher) {
            repository.deleteNote(noteId)
            withContext(dispatcherMain) {
                folderLiveDataWrapper.decrement()
                clear.clear(EditNoteViewModel::class.java)
                navigation.update(FolderDetailsScreen)
            }
        }
    }

    fun renameNote(noteId: Long, newText: String) {
        viewModelScope.launch(dispatcher) {
            repository.renameNote(noteId, newText)
            withContext(dispatcherMain) {
                noteListLiveDataWrapper.update(noteId, newText)
                clear.clear(EditNoteViewModel::class.java)
                navigation.update(FolderDetailsScreen)
            }
        }
    }

    fun comeback() {
        clear.clear(EditNoteViewModel::class.java)
        navigation.update(FolderDetailsScreen)
    }
}
