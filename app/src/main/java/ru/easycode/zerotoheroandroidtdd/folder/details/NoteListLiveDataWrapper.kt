package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface NoteListLiveDataWrapper {

    interface Update {
        fun update(noteId: Long, newText: String)
    }

    interface UpdateListAndRead {
        fun update(notes: List<NoteUi>)
    }

    interface Observe {
        fun observe(): LiveData<List<NoteUi>> = MutableLiveData()
    }

    interface Create {
        fun create(noteUi: NoteUi)
    }

    class Base : Update, UpdateListAndRead, Observe, Create {
        private val liveData = MutableLiveData<List<NoteUi>>()

        override fun update(notes: List<NoteUi>) {
            liveData.value = notes
        }

        override fun update(noteId: Long, newText: String) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            val index = list.indexOfFirst { it.id == noteId }
            if (index != -1) {
                list[index] = list[index].copy(title = newText)
                liveData.value = list
            }
        }

        override fun observe(): LiveData<List<NoteUi>> = liveData

        override fun create(noteUi: NoteUi) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(noteUi)
            liveData.value = list
        }
    }
}
