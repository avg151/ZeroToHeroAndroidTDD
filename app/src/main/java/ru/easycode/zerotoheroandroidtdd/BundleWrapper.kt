package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle

interface BundleWrapper {

    interface Save {
        fun save(uiState: UiState)
    }

    interface Restore {
        fun restore(): UiState
    }

    interface Mutable : Save, Restore

    class Base(private val bundle: Bundle) : Mutable {

        override fun save(uiState: UiState) {
            bundle.putSerializable(KEY, uiState)
        }

        override fun restore(): UiState {
            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(KEY, UiState::class.java) ?: UiState.Empty
            } else {
                @Suppress("DEPRECATION")
                bundle.getSerializable(KEY) as? UiState ?: UiState.Empty
            }
        }

        companion object {
            private const val KEY = "UiStateKey"
        }
    }
}