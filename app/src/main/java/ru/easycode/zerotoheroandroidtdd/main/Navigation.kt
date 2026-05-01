package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface Navigation {

    interface Update {
        fun update(screen: Screen)
    }

    interface Observe {
        fun observe(): LiveData<Screen> = MutableLiveData()
    }

    interface Mutable : Update, Observe

    class Base : Mutable {
        private val liveData = MutableLiveData<Screen>()

        override fun update(screen: Screen) {
            liveData.value = screen
        }

        override fun observe(): LiveData<Screen> = liveData
    }
}
