package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

interface Count : Serializable {
    fun increment(): Count

    data class Base(
        private val value: Int,
        private val step: Int
    ) : Count {
        override fun increment(): Count {
            return Base(value + step, step)
        }

        override fun toString(): String {
            return value.toString()
        }
    }
}
