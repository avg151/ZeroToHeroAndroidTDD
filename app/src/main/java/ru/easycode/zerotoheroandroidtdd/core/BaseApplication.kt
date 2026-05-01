package ru.easycode.zerotoheroandroidtdd.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.main.ViewModelsFactory
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.core.Now
import ru.easycode.zerotoheroandroidtdd.note.edit.NoteLiveDataWrapper

class BaseApplication : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory
    
    lateinit var navigation: Navigation.Base
    lateinit var folderListLiveDataWrapper: FolderListLiveDataWrapper.Base
    lateinit var folderLiveDataWrapper: FolderLiveDataWrapper.Base
    lateinit var noteListLiveDataWrapper: NoteListLiveDataWrapper.Base
    lateinit var noteLiveDataWrapper: NoteLiveDataWrapper.Base

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init() {
        val db = Room.inMemoryDatabaseBuilder(this, AppDataBase::class.java).build()
        val now = Now.Base()
        val foldersRepository = FoldersRepository.Base(now, db.foldersDao(), db.notesDao())
        val notesRepository = NotesRepository.Base(now, db.notesDao())
        navigation = Navigation.Base()
        folderListLiveDataWrapper = FolderListLiveDataWrapper.Base()
        folderLiveDataWrapper = FolderLiveDataWrapper.Base()
        noteListLiveDataWrapper = NoteListLiveDataWrapper.Base()
        noteLiveDataWrapper = NoteLiveDataWrapper.Base()

        factory = ProvideViewModel.Factory(object : ProvideViewModel {
            override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
                return ViewModelsFactory(
                    foldersRepository,
                    notesRepository,
                    navigation,
                    folderListLiveDataWrapper,
                    folderLiveDataWrapper,
                    noteListLiveDataWrapper,
                    noteLiveDataWrapper,
                    factory
                ).viewModel(clasz)
            }
        })
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T = factory.viewModel(clasz)
}
