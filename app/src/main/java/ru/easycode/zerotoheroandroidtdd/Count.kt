package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

interface Count : Serializable {

    fun isMax(): Boolean
    fun isMin(): Boolean
    fun increment(): Count
    fun decrement(): Count

    data class Base(
        private val min: Int,
        private val max: Int,
        private val value: Int,
        private val step: Int
    ) : Count {

        override fun isMax(): Boolean = value >= max

        override fun isMin(): Boolean = value <= min

        override fun increment(): Count {
            return Base(min, max, (value + step).coerceAtMost(max), step)
        }

        override fun decrement(): Count {
            return Base(min, max, (value - step).coerceAtLeast(min), step)
        }

        override fun toString(): String = value.toString()
    }
}
