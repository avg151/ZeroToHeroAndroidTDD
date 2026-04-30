package ru.easycode.zerotoheroandroidtdd

interface Count {
    fun increment(number: String): UiState

    class Base(private val step: Int, private val max: Int) : Count {
        init {
            if (step <= 0) throw IllegalStateException("step should be positive, but was $step")
            if (max <= 0) throw IllegalStateException("max should be positive, but was $max")
            if (max < step) throw IllegalStateException("max should be more than step")
        }

        override fun increment(number: String): UiState {
            val next = number.toInt() + step
            return if (next + step > max) {
                UiState.Max(next.toString())
            } else {
                UiState.Base(next.toString())
            }
        }
    }
}
