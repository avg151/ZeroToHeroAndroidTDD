package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData

class MainViewModel(private val listLiveDataWrapper: ListLiveDataWrapper) {

    fun liveData(): LiveData<List<CharSequence>> = listLiveDataWrapper.liveData()

    fun add(text: String) {
        listLiveDataWrapper.add(text)
    }

    fun save(bundle: BundleWrapper.Save) {
        listLiveDataWrapper.save(bundle)
    }

    fun restore(bundle: BundleWrapper.Restore) {
        val list = bundle.restore()
        listLiveDataWrapper.update(list)
    }
}
