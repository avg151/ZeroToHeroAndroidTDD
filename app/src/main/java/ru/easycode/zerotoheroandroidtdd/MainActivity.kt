package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var isTextViewRemoved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val removeButton = findViewById<Button>(R.id.removeButton)

        if (savedInstanceState != null) {
            isTextViewRemoved = savedInstanceState.getBoolean(KEY_REMOVED, false)
        }

        if (isTextViewRemoved) {
            rootLayout.removeView(titleTextView)
        }

        removeButton.setOnClickListener {
            rootLayout.removeView(titleTextView)
            isTextViewRemoved = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_REMOVED, isTextViewRemoved)
    }

    companion object {
        private const val KEY_REMOVED = "isTextViewRemoved"
    }
}