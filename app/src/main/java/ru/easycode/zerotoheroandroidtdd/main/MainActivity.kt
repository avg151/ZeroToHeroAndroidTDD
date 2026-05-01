package ru.easycode.zerotoheroandroidtdd.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.BaseApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as BaseApplication
        if (savedInstanceState == null) {
            app.init()
        }
        val viewModel = app.viewModel(MainViewModel::class.java)

        app.navigation.observe().observe(this) { screen ->
            screen.show(R.id.container, supportFragmentManager)
        }

        viewModel.init(savedInstanceState == null)
    }
}
