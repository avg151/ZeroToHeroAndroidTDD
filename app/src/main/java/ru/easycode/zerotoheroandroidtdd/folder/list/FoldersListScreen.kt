package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

object FoldersListScreen : Screen {
    override fun show(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(containerId, FoldersListFragment())
            .commit()
    }
}
