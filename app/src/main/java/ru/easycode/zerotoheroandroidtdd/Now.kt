package ru.easycode.zerotoheroandroidtdd

interface Now {
    fun nowMillis(): Long

    class Base : Now {
        override fun nowMillis(): Long = System.currentTimeMillis()
    }
}