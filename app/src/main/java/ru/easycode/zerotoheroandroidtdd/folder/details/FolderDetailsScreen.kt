package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

object FolderDetailsScreen : Screen {
    override fun show(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(containerId, FolderDetailsFragment())
            .commit()
    }
}
