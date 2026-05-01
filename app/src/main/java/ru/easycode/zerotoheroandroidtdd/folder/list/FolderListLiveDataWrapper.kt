package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface FolderListLiveDataWrapper {

    interface UpdateListAndRead : Update, Observe

    interface Update {
        fun update(list: List<FolderUi>)
    }

    interface Observe {
        fun observe(): LiveData<List<FolderUi>> = MutableLiveData()
    }

    interface Create {
        fun create(folderUi: FolderUi)
    }

    class Base : UpdateListAndRead, Create {
        private val liveData = MutableLiveData<List<FolderUi>>()

        override fun update(list: List<FolderUi>) {
            liveData.value = list
        }

        override fun observe(): LiveData<List<FolderUi>> = liveData

        override fun create(folderUi: FolderUi) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(folderUi)
            liveData.value = list
        }
    }
}
