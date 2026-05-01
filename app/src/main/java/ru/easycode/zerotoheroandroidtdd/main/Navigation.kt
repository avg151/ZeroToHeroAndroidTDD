package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.SingleLiveEvent

interface Navigation {
    interface Update {
        fun update(value: Screen)
    }

    interface Observe {
        fun liveData(): LiveData<Screen>
    }

    interface Mutable : Update, Observe

    class Base(
        private val liveData: MutableLiveData<Screen> = SingleLiveEvent()
    ) : Mutable {
        override fun update(value: Screen) {
            liveData.value = value
        }

        override fun liveData(): LiveData<Screen> = liveData
    }
}
