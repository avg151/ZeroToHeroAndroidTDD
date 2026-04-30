package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isRemoved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<ViewGroup>(R.id.rootLayout)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val removeButton = findViewById<Button>(R.id.removeButton)

        if (savedInstanceState != null) {
            isRemoved = savedInstanceState.getBoolean("isRemoved", false)
        }

        if (isRemoved) {
            rootLayout.removeView(titleTextView)
            removeButton.isEnabled = false
        }

        removeButton.setOnClickListener {
            rootLayout.removeView(titleTextView)
            removeButton.isEnabled = false
            isRemoved = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isRemoved", isRemoved)
    }
}