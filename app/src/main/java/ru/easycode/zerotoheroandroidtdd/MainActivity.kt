package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<TextInputEditText>(R.id.inputEditText)
        val button = findViewById<Button>(R.id.actionButton)
        val title = findViewById<TextView>(R.id.titleTextView)

        if (savedInstanceState != null) {
            title.text = savedInstanceState.getCharSequence("title")
        }

        button.setOnClickListener {
            val text = input.text.toString()
            title.text = text
            input.setText("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val title = findViewById<TextView>(R.id.titleTextView)
        outState.putCharSequence("title", title.text)
    }
}