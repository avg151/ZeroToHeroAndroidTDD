package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    interface Read {
        fun liveData(): LiveData<List<ItemUi>>
    }

    interface Update {
        fun update(value: List<ItemUi>)
    }

    interface Add {
        fun add(value: ItemUi)
    }

    interface Delete {
        fun delete(item: ItemUi)
    }

    interface Mutable : Read, Update

    interface All : Mutable, Add, Delete

    class Base : All {
        private val liveData = MutableLiveData<List<ItemUi>>()

        override fun liveData(): LiveData<List<ItemUi>> = liveData

        override fun update(value: List<ItemUi>) {
            liveData.value = value
        }

        override fun add(value: ItemUi) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(value)
            liveData.value = list
        }

        override fun delete(item: ItemUi) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.remove(item)
            liveData.value = list
        }
    }
}
