package ru.easycode.zerotoheroandroidtdd.note.create

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

data class CreateNoteScreen(private val folderId: Long) : Screen {
    override fun show(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(containerId, CreateNoteFragment.newInstance(folderId))
            .commit()
    }
}
