package ru.easycode.zerotoheroandroidtdd

interface Count {
    fun increment(number: String): String

    class Base(private val step: Int) : Count {
        init {
            if (step <= 0) {
                throw IllegalStateException("step should be positive, but was $step")
            }
        }

        override fun increment(number: String): String {
            return (number.toInt() + step).toString()
        }
    }
}
