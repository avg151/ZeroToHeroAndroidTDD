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

        count = Count.Base(step = 2, max = 4, min = 0)

        val countTextView = findViewById<TextView>(R.id.countTextView)
        val incrementButton = findViewById<Button>(R.id.incrementButton)
        val decrementButton = findViewById<Button>(R.id.decrementButton)

        val initialValue = savedInstanceState?.getString("count") ?: "0"
        count.initial(initialValue).apply(countTextView, incrementButton, decrementButton)

        incrementButton.setOnClickListener {
            count.increment(countTextView.text.toString())
                .apply(countTextView, incrementButton, decrementButton)
        }

        decrementButton.setOnClickListener {
            count.decrement(countTextView.text.toString())
                .apply(countTextView, incrementButton, decrementButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val countTextView = findViewById<TextView>(R.id.countTextView)
        outState.putString("count", countTextView.text.toString())
    }
}
