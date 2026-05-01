package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FolderCache::class, NoteCache::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun foldersDao(): FoldersDao
    abstract fun notesDao(): NotesDao
}
