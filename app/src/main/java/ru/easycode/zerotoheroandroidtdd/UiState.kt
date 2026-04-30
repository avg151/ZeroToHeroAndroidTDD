package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

sealed interface UiState {

    fun apply(
        progressBar: ProgressBar,
        actionButton: Button,
        titleTextView: TextView
    )

    object ShowProgress : UiState {
        override fun apply(
            progressBar: ProgressBar,
            actionButton: Button,
            titleTextView: TextView
        ) {
            progressBar.visibility = View.VISIBLE
            actionButton.isEnabled = false
            titleTextView.visibility = View.GONE
        }
    }

    object ShowData : UiState {
        override fun apply(
            progressBar: ProgressBar,
            actionButton: Button,
            titleTextView: TextView
        ) {
            progressBar.visibility = View.GONE
            actionButton.isEnabled = true
            titleTextView.visibility = View.VISIBLE
        }
    }
}
