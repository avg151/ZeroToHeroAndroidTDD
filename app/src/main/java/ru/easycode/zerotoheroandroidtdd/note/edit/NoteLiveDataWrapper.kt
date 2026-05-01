package ru.easycode.zerotoheroandroidtdd.note.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface NoteLiveDataWrapper {

    fun update(noteText: String)

    fun observe(): LiveData<String> = MutableLiveData()

    class Base : NoteLiveDataWrapper {
        private val liveData = MutableLiveData<String>()

        override fun update(noteText: String) {
            liveData.value = noteText
        }

        override fun observe(): LiveData<String> = liveData
    }
}
