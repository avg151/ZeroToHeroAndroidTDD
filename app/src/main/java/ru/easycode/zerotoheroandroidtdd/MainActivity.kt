package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        val changeButton = findViewById<Button>(R.id.changeButton)

        changeButton.setOnClickListener {
            titleTextView.text = "I am an Android Developer!"
        }

        if (savedInstanceState != null) {
            titleTextView.text = savedInstanceState.getString(KEY_TEXT)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TEXT, titleTextView.text.toString())
    }

    companion object {
        private const val KEY_TEXT = "title_text"
    }
}