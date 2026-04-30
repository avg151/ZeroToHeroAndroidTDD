package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var count: Count

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        count = Count.Base(step = 2, max = 4)

        val textView = findViewById<TextView>(R.id.countTextView)
        val button = findViewById<Button>(R.id.incrementButton)

        button.setOnClickListener {
            val state = count.increment(textView.text.toString())
            state.apply(textView, button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val textView = findViewById<TextView>(R.id.countTextView)
        val button = findViewById<Button>(R.id.incrementButton)
        outState.putString("count", textView.text.toString())
        outState.putBoolean("enabled", button.isEnabled)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textView = findViewById<TextView>(R.id.countTextView)
        val button = findViewById<Button>(R.id.incrementButton)
        textView.text = savedInstanceState.getString("count")
        button.isEnabled = savedInstanceState.getBoolean("enabled")
    }
}
