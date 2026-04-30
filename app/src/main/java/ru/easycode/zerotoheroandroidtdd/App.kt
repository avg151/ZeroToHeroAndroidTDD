package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), ProvideViewModel {

    private lateinit var factory: MainViewModel.Factory

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SimpleService::class.java)

        val repository = Repository.Base(
            service = service,
            url = "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/task/018-clouddatasource/app/sampleresponse.json"
        )

        val liveDataWrapper = LiveDataWrapper.Base()

        factory = MainViewModel.Factory(
            liveDataWrapper = liveDataWrapper,
            repository = repository
        )
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, factory)[clazz]
    }
}
