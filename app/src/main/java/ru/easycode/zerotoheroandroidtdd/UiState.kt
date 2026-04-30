package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView

sealed class UiState {
    abstract fun apply(
        countTextView: TextView,
        incrementButton: Button,
        decrementButton: Button
    )

    data class Min(private val text: String) : UiState() {
        override fun apply(
            countTextView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            countTextView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = false
        }
    }

    data class Max(private val text: String) : UiState() {
        override fun apply(
            countTextView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            countTextView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }
    }

    data class Base(private val text: String) : UiState() {
        override fun apply(
            countTextView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            countTextView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = true
        }
    }
}
