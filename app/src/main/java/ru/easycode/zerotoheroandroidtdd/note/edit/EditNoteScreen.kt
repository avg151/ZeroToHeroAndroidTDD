package ru.easycode.zerotoheroandroidtdd.note.edit

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

data class EditNoteScreen(private val noteId: Long) : Screen {
    override fun show(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(containerId, EditNoteFragment.newInstance(noteId))
            .commit()
    }
}
