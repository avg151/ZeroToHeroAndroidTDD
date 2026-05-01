package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.create.CreateScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class ListViewModel(
    private val liveDataWrapper: ListLiveDataWrapper.Mutable,
    private val navigation: Navigation.Update
) : ViewModel() {

    fun liveData(): LiveData<List<CharSequence>> = liveDataWrapper.liveData()

    fun create() {
        navigation.update(CreateScreen)
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        liveDataWrapper.update(bundleWrapper.restore())
    }
}
