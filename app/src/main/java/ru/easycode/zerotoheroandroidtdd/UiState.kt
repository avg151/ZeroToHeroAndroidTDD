package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView

interface UiState {
    fun apply(textView: TextView, button: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = true
        }
    }

    data class Max(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = false
        }
    }
}
