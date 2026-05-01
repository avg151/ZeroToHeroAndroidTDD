package ru.easycode.zerotoheroandroidtdd.note.core

interface Now {
    fun timeInMillis(): Long

    class Base : Now {
        override fun timeInMillis(): Long = System.currentTimeMillis()
    }
}
