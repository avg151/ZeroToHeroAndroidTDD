package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val actionButton = findViewById<Button>(R.id.actionButton)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)

        val viewModel = (application as App).viewModel(this)

        viewModel.liveData().observe(this) { state ->
            state.apply(progressBar, actionButton, titleTextView)
        }

        actionButton.setOnClickListener {
            viewModel.load()
        }

        if (savedInstanceState != null) {
            viewModel.restore(BundleWrapper.Base(savedInstanceState))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val viewModel = (application as App).viewModel(this)
        viewModel.save(BundleWrapper.Base(outState))
    }
}