package ru.easycode.zerotoheroandroidtdd.folder.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi

interface FolderLiveDataWrapper {

    interface Update {
        fun update(folder: FolderUi)
    }

    interface Observe {
        fun observe(): LiveData<FolderUi> = MutableLiveData()
    }

    interface Increment {
        fun increment()
    }

    interface Decrement {
        fun decrement()
    }

    interface Rename {
        fun rename(newName: String)
    }

    interface Mutable : Update, Observe {
        fun folderId(): Long
    }

    class Base : Mutable, Increment, Decrement, Rename {
        private val liveData = MutableLiveData<FolderUi>()

        override fun update(folder: FolderUi) {
            liveData.value = folder
        }

        override fun observe(): LiveData<FolderUi> = liveData

        override fun folderId(): Long = liveData.value?.id ?: -1L

        override fun increment() {
            liveData.value = liveData.value?.copy(notesCount = liveData.value!!.notesCount + 1)
        }

        override fun decrement() {
            liveData.value = liveData.value?.copy(notesCount = liveData.value!!.notesCount - 1)
        }

        override fun rename(newName: String) {
            liveData.value = liveData.value?.copy(title = newName)
        }
    }
}
