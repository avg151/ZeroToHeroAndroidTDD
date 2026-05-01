package ru.easycode.zerotoheroandroidtdd.folder.create

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

object CreateFolderScreen : Screen {
    override fun show(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(containerId, CreateFolderFragment())
            .commit()
    }
}
