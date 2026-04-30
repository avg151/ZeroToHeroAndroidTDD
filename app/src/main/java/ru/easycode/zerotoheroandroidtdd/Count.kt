package ru.easycode.zerotoheroandroidtdd

interface Count {
    fun initial(number: String): UiState
    fun increment(number: String): UiState
    fun decrement(number: String): UiState

    class Base(
        private val step: Int,
        private val max: Int,
        private val min: Int
    ) : Count {
        init {
            if (step <= 0) throw IllegalStateException("step should be positive, but was $step")
            if (max <= 0) throw IllegalStateException("max should be positive, but was $max")
            if (max <= step) throw IllegalStateException("max should be more than step")
            if (max <= min) throw IllegalStateException("max should be more than min")
        }

        override fun initial(number: String): UiState {
            val value = number.toInt()
            return when {
                value <= min -> UiState.Min(value.toString())
                value >= max -> UiState.Max(value.toString())
                else -> UiState.Base(value.toString())
            }
        }

        override fun increment(number: String): UiState {
            val value = number.toInt() + step
            return if (value >= max) UiState.Max(max.toString())
            else UiState.Base(value.toString())
        }

        override fun decrement(number: String): UiState {
            val value = number.toInt() - step
            return if (value <= min) UiState.Min(min.toString())
            else UiState.Base(value.toString())
        }
    }
}
