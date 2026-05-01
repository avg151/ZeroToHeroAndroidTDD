package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderScreen
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteScreen
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteScreen

class FolderDetailsViewModel(
    private val noteListRepository: NotesRepository.ReadList,
    private val liveDataWrapper: NoteListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Mutable,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun init() {
        val folderId = folderLiveDataWrapper.folderId()
        viewModelScope.launch(dispatcher) {
            val notes = noteListRepository.noteList(folderId)
            val notesUi = notes.map { NoteUi(it.id, it.title, it.folderId) }
            withContext(dispatcherMain) {
                liveDataWrapper.update(notesUi)
            }
        }
    }

    fun createNote() {
        val folderId = folderLiveDataWrapper.folderId()
        navigation.update(CreateNoteScreen(folderId))
    }

    fun editNote(noteUi: NoteUi) {
        navigation.update(EditNoteScreen(noteUi.id))
    }

    fun editFolder() {
        val folderId = folderLiveDataWrapper.folderId()
        navigation.update(EditFolderScreen(folderId))
    }

    fun comeback() {
        clear.clear(FolderDetailsViewModel::class.java)
        navigation.update(FoldersListScreen)
    }
}
