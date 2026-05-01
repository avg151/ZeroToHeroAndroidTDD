package ru.easycode.zerotoheroandroidtdd

interface ProvideTime {
    fun now(): Long

    class Base : ProvideTime {
        override fun now(): Long = System.currentTimeMillis()
    }
}
