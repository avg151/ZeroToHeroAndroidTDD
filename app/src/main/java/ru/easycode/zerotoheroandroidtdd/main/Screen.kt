package ru.easycode.zerotoheroandroidtdd.main

import androidx.fragment.app.FragmentManager

interface Screen {
    fun show(containerId: Int, fragmentManager: FragmentManager)
}
