package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var countTextView: TextView
    private lateinit var count: Count

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countTextView = findViewById(R.id.countTextView)
        val incrementButton = findViewById<Button>(R.id.incrementButton)
        count = Count.Base(step = 2)

        if (savedInstanceState != null) {
            countTextView.text = savedInstanceState.getString(KEY_COUNT)
        }

        incrementButton.setOnClickListener {
            val currentText = countTextView.text.toString()
            countTextView.text = count.increment(currentText)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_COUNT, countTextView.text.toString())
    }

    companion object {
        private const val KEY_COUNT = "count"
    }
}
