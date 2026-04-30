package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData

interface LiveDataWrapper {

    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    fun save(bundleWrapper: BundleWrapper.Save)

    interface Update {
        fun update(value: UiState)
    }

    interface Mutable : LiveDataWrapper, Update

    class Base(
        private val liveData: SingleLiveEvent<UiState> = SingleLiveEvent()
    ) : Mutable {

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData(): LiveData<UiState> = liveData

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let {
                bundleWrapper.save(it)
            }
        }
    }
}
