package ru.easycode.zerotoheroandroidtdd.folder.edit

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

data class EditFolderScreen(private val folderId: Long) : Screen {
    override fun show(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(containerId, EditFolderFragment.newInstance(folderId))
            .commit()
    }
}
