package ru.easycode.zerotoheroandroidtdd.folder.core

import ru.easycode.zerotoheroandroidtdd.core.FolderCache
import ru.easycode.zerotoheroandroidtdd.core.FoldersDao
import ru.easycode.zerotoheroandroidtdd.core.NotesDao
import ru.easycode.zerotoheroandroidtdd.note.core.Now

interface FoldersRepository {

    interface ReadList {
        suspend fun folders(): List<Folder>
    }

    interface Create {
        suspend fun createFolder(name: String): Long
    }

    interface Delete {
        suspend fun delete(folderId: Long)
    }

    interface Rename {
        suspend fun rename(folderId: Long, newName: String)
    }

    interface Edit : Delete, Rename

    class Base(
        private val now: Now,
        private val foldersDao: FoldersDao,
        private val notesDao: NotesDao
    ) : ReadList, Create, Delete, Rename, Edit {

        override suspend fun folders(): List<Folder> {
            val folders = foldersDao.folders()
            return folders.map { folderCache ->
                val notes = notesDao.notes(folderCache.id)
                Folder(
                    id = folderCache.id,
                    title = folderCache.text,
                    notesCount = notes.size
                )
            }
        }

        override suspend fun createFolder(name: String): Long {
            val id = now.timeInMillis()
            foldersDao.insert(FolderCache(id = id, text = name))
            return id
        }

        override suspend fun delete(folderId: Long) {
            foldersDao.delete(folderId)
            notesDao.deleteByFolderId(folderId)
        }

        override suspend fun rename(folderId: Long, newName: String) {
            foldersDao.insert(FolderCache(id = folderId, text = newName))
        }
    }
}
