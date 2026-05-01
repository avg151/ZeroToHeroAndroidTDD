package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ClearViewModel {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToMain()
        }
    }

    fun navigateToMain() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commit()
    }

    fun navigateToAdd() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddFragment())
            .commit()
    }

    override fun clearViewModel(clasz: Class<out ViewModel>) {
        (application as TaskApp).clear(clasz)
        navigateToMain()
    }
}