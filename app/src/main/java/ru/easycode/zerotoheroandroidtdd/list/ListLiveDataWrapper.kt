package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.SingleLiveEvent

interface ListLiveDataWrapper {

    interface Add {
        fun add(new: CharSequence)
    }

    interface Save {
        fun save(bundle: BundleWrapper.Save)
    }

    interface Update {
        fun update(list: List<CharSequence>)
    }

    interface Observe {
        fun liveData(): LiveData<List<CharSequence>>
    }

    interface All : Mutable, Observe
    interface Mutable : Add, Save, Update, Observe

    class Base(
        private val liveData: MutableLiveData<List<CharSequence>> = SingleLiveEvent()
    ) : All {

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
