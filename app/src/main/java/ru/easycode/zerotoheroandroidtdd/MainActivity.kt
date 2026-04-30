package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = MainRepository()
        val liveDataWrapper = MainLiveDataWrapper()
        viewModel = MainViewModel(repository, liveDataWrapper)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val actionButton = findViewById<Button>(R.id.actionButton)

        viewModel.liveData().observe(this) { state ->
            when (state) {
                is UiState.ShowProgress -> {
                    progressBar.visibility = View.VISIBLE
                    titleTextView.visibility = View.GONE
                    actionButton.isEnabled = false
                }

                is UiState.ShowData -> {
                    progressBar.visibility = View.GONE
                    titleTextView.visibility = View.VISIBLE
                    actionButton.isEnabled = true
                }
            }
        }

        actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}