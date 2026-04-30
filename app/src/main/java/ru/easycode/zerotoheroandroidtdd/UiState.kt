package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.Serializable

sealed class UiState : Serializable {

    abstract fun apply(
        progressBar: ProgressBar,
        actionButton: Button,
        titleTextView: TextView
    )

    object ShowProgress : UiState() {
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

    object ShowData : UiState() {
        override fun apply(
            progressBar: ProgressBar,
            actionButton: Button,
            titleTextView: TextView
        ) {
            progressBar.visibility = View.GONE
            actionButton.isEnabled = true
            titleTextView.visibility = View.VISIBLE
            titleTextView.text = "Done"
        }
    }

    object Empty : UiState() {
        override fun apply(
            progressBar: ProgressBar,
            actionButton: Button,
            titleTextView: TextView
        ) {
            progressBar.visibility = View.GONE
            actionButton.isEnabled = true
            titleTextView.visibility = View.GONE
        }
    }
}