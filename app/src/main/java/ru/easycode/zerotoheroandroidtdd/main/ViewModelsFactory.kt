package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.create.CreateFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsViewModel
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListViewModel
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteViewModel
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteViewModel
import ru.easycode.zerotoheroandroidtdd.note.edit.NoteLiveDataWrapper

class ViewModelsFactory(
    private val foldersRepository: FoldersRepository.Base,
    private val notesRepository: NotesRepository.Base,
    private val navigation: Navigation.Mutable,
    private val folderListLiveDataWrapper: FolderListLiveDataWrapper.Base,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Base,
    private val noteListLiveDataWrapper: NoteListLiveDataWrapper.Base,
    private val noteLiveDataWrapper: NoteLiveDataWrapper.Base,
    private val clearViewModels: ClearViewModels
) : ProvideViewModel {

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        val dispatcher = Dispatchers.Main
        val dispatcherMain = Dispatchers.Main
        return when (clasz) {
            MainViewModel::class.java -> MainViewModel(navigation)
            FolderListViewModel::class.java -> FolderListViewModel(
                foldersRepository,
                folderListLiveDataWrapper,
                folderLiveDataWrapper,
                navigation,
                dispatcher,
                dispatcherMain
            )
            CreateFolderViewModel::class.java -> CreateFolderViewModel(
                foldersRepository,
                folderListLiveDataWrapper,
                navigation,
                clearViewModels,
                dispatcher,
                dispatcherMain
            )
            FolderDetailsViewModel::class.java -> FolderDetailsViewModel(
                notesRepository,
                noteListLiveDataWrapper,
                folderLiveDataWrapper,
                navigation,
                clearViewModels,
                dispatcher,
                dispatcherMain
            )
            EditFolderViewModel::class.java -> EditFolderViewModel(
                folderLiveDataWrapper,
                foldersRepository,
                navigation,
                clearViewModels,
                dispatcher,
                dispatcherMain
            )
            CreateNoteViewModel::class.java -> CreateNoteViewModel(
                folderLiveDataWrapper,
                noteListLiveDataWrapper,
                notesRepository,
                navigation,
                clearViewModels,
                dispatcher,
                dispatcherMain
            )
            EditNoteViewModel::class.java -> EditNoteViewModel(
                folderLiveDataWrapper,
                noteLiveDataWrapper,
                noteListLiveDataWrapper,
                notesRepository,
                navigation,
                clearViewModels,
                dispatcher,
                dispatcherMain
            )
            else -> throw IllegalArgumentException("Unknown ViewModel class $clasz")
        } as T
    }
}
