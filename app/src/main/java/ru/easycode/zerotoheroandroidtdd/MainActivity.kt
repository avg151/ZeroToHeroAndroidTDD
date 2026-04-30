package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private var isTextViewVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        val hideButton = findViewById<Button>(R.id.hideButton)

        if (savedInstanceState != null) {
            isTextViewVisible = savedInstanceState.getBoolean(KEY_VISIBILITY, true)
        }

        updateTextViewVisibility()

        hideButton.setOnClickListener {
            isTextViewVisible = false
            updateTextViewVisibility()
        }
    }

    private fun updateTextViewVisibility() {
        titleTextView.visibility = if (isTextViewVisible) View.VISIBLE else View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VISIBILITY, isTextViewVisible)
    }

    companion object {
        private const val KEY_VISIBILITY = "textViewVisibility"
    }
}