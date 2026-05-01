package ru.easycode.zerotoheroandroidtdd.note.core

import ru.easycode.zerotoheroandroidtdd.core.NoteCache
import ru.easycode.zerotoheroandroidtdd.core.NotesDao

interface NotesRepository {

    interface ReadList {
        suspend fun noteList(folderId: Long): List<MyNote>
    }

    interface Create {
        suspend fun createNote(folderId: Long, text: String): Long
    }

    interface Delete {
        suspend fun deleteNote(noteId: Long)
    }

    interface Rename {
        suspend fun renameNote(noteId: Long, newName: String)
    }

    interface Read {
        suspend fun note(noteId: Long): MyNote
    }

    interface Edit : Delete, Rename, Read

    class Base(
        private val now: Now,
        private val dao: NotesDao
    ) : ReadList, Create, Delete, Rename, Read, Edit {

        override suspend fun noteList(folderId: Long): List<MyNote> {
            return dao.notes(folderId).map {
                MyNote(id = it.id, title = it.text, folderId = it.folderId)
            }
        }

        override suspend fun createNote(folderId: Long, text: String): Long {
            val id = now.timeInMillis()
            dao.insert(NoteCache(id = id, folderId = folderId, text = text))
            return id
        }

        override suspend fun deleteNote(noteId: Long) {
            dao.delete(noteId)
        }

        override suspend fun renameNote(noteId: Long, newName: String) {
            val note = dao.note(noteId)
            dao.insert(NoteCache(id = note.id, folderId = note.folderId, text = newName))
        }

        override suspend fun note(noteId: Long): MyNote {
            val note = dao.note(noteId)
            return MyNote(id = note.id, title = note.text, folderId = note.folderId)
        }
    }
}
