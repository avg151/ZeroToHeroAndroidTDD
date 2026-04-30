package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData

interface LiveDataWrapper {

    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    fun save(bundleWrapper: BundleWrapper.Save)

    class Base(
        private val liveData: SingleLiveEvent<UiState> = SingleLiveEvent()
    ) : LiveDataWrapper {

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
