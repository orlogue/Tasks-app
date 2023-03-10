package com.example.tasks.data.db.dao

import androidx.room.*
import com.example.tasks.data.entities.NoteDbEntity
import com.example.tasks.data.entities.NotesListDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("INSERT INTO lists VALUES (1, 'Starred'), (2, 'My Tasks')")
    suspend fun initDatabase()

    @Query("DELETE FROM lists")
    suspend fun deleteLists()

    @Query("SELECT * FROM lists")
    fun getLists(): Flow<List<NotesListDbEntity>>

    @Insert
    suspend fun createList(notesListDbEntity: NotesListDbEntity)

    @Update
    suspend fun renameList(notesListDbEntity: NotesListDbEntity)

    @Query("DELETE FROM lists WHERE id == :listId")
    suspend fun deleteList(listId: Int)


    @Query("SELECT * FROM notes ORDER BY id desc")
    fun getNotes(): Flow<List<NoteDbEntity>>

    @Insert
    suspend fun createNote(noteDbEntity: NoteDbEntity)

    @Update
    suspend fun updateNote(noteDbEntity: NoteDbEntity)

    @Query("DELETE FROM notes WHERE id == :noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("DELETE FROM notes WHERE isCompleted == 1 AND list_id == :listId")
    suspend fun deleteCompletedNotes(listId: Int)
}