package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<TextInputEditText>(R.id.inputEditText)
        val button = findViewById<Button>(R.id.actionButton)
        val title = findViewById<TextView>(R.id.titleTextView)

        button.isEnabled = input.text?.length.isMoreOrEqual(3)

        input.doAfterTextChanged { s ->
            button.isEnabled = s?.length.isMoreOrEqual(3)
        }

        if (savedInstanceState != null) {
            val savedTitle = savedInstanceState.getCharSequence(TITLE_KEY)
            if (savedTitle != null) {
                title.text = savedTitle
            }
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
        outState.putCharSequence(TITLE_KEY, title.text)
    }

    companion object {
        private const val TITLE_KEY = "title_key"
    }
}

private fun Int?.isMoreOrEqual(length: Int): Boolean {
    return (this ?: 0) >= length
}