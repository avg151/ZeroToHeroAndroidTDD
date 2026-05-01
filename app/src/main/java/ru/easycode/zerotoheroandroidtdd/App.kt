package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class App : Application(), ViewModelProvider.Factory {

    private lateinit var viewModel: ListViewModel

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "records_db"
        ).build()

        viewModel = ListViewModel(
            dao = db.dao(),
            provideTime = ProvideTime.Base(),
            runAsync = RunAsync.Base()
        )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
