package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    fun liveData(): LiveData<List<CharSequence>>

    fun add(new: CharSequence)

    fun save(bundle: BundleWrapper.Save)

    fun update(list: List<CharSequence>)

    class Base(
        private val liveData: MutableLiveData<List<CharSequence>> = SingleLiveEvent()
    ) : ListLiveDataWrapper {

        private val list = mutableListOf<CharSequence>()

        override fun liveData(): LiveData<List<CharSequence>> = liveData

        override fun add(new: CharSequence) {
            list.add(new)
            liveData.value = ArrayList(list)
        }

        override fun save(bundle: BundleWrapper.Save) {
            bundle.save(ArrayList(list))
        }

        override fun update(list: List<CharSequence>) {
            this.list.clear()
            this.list.addAll(list)
            liveData.value = ArrayList(this.list)
        }
    }
}
