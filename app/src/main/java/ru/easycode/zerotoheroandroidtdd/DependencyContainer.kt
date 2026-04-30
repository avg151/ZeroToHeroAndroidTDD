package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DependencyContainer : ViewModelProvider.Factory {

    class Base : DependencyContainer {

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service = retrofit.create(SimpleService::class.java)

        private val repository = Repository.Base(
            service = service,
            url = "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/task/018-clouddatasource/app/sampleresponse.json"
        )

        private var liveDataWrapper: LiveDataWrapper? = null

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass == MainViewModel::class.java) {
                if (liveDataWrapper == null) {
                    liveDataWrapper = LiveDataWrapper.Base()
                }
                MainViewModel(
                    liveDataWrapper = liveDataWrapper!!,
                    repository = repository
                ) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class $modelClass")
            }
        }
    }
}
