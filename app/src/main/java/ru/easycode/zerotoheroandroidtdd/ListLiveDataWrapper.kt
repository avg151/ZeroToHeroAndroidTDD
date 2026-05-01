package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    interface Read {
        fun liveData(): LiveData<List<String>>
    }

    interface Update {
        fun update(value: List<String>)
    }

    interface Add {
        fun add(value: String)
    }

    interface Mutable : Read, Update

    class Base : Mutable, Add {
        private val liveData = MutableLiveData<List<String>>()

        override fun liveData(): LiveData<List<String>> = liveData

        override fun update(value: List<String>) {
            liveData.value = value
        }

        override fun add(value: String) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(value)
            liveData.value = list
        }
    }
}