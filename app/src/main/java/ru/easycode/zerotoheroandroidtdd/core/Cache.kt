package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "folders_table")
data class FolderCache(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "text")
    val text: String
)

@Entity(tableName = "notes_table")
data class NoteCache(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "folder_id")
    val folderId: Long,
    @ColumnInfo(name = "text")
    val text: String
)

@Dao
interface FoldersDao {
    @Query("SELECT * FROM folders_table")
    suspend fun folders(): List<FolderCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: FolderCache)

    @Query("DELETE FROM folders_table WHERE id = :folderId")
    suspend fun delete(folderId: Long)
}

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table WHERE folder_id = :folderId")
    suspend fun notes(folderId: Long): List<NoteCache>

    @Query("SELECT * FROM notes_table WHERE id = :noteId")
    suspend fun note(noteId: Long): NoteCache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteCache)

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("DELETE FROM notes_table WHERE folder_id = :folderId")
    suspend fun deleteByFolderId(folderId: Long)
}
