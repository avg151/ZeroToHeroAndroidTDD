package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var contentLayout: LinearLayout
    private val items = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<TextInputEditText>(R.id.inputEditText)
        val button = findViewById<Button>(R.id.actionButton)
        contentLayout = findViewById(R.id.contentLayout)

        savedInstanceState?.getStringArrayList(ITEMS_KEY)?.let { savedItems ->
            items.addAll(savedItems)
            items.forEach { text ->
                addTextView(text)
            }
        }

        button.setOnClickListener {
            val text = input.text.toString()
            items.add(text)
            addTextView(text)
            input.setText("")
        }
    }

    private fun addTextView(text: String) {
        val textView = TextView(this)
        textView.text = text
        contentLayout.addView(textView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(ITEMS_KEY, ArrayList(items))
    }

    companion object {
        private const val ITEMS_KEY = "items_key"
    }
}