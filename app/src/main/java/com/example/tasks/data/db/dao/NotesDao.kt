package com.example.tasks.data.db.dao

import androidx.room.*
import com.example.tasks.data.db.models.NoteDbEntity
import com.example.tasks.data.db.models.NotesListDbEntity
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


    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteDbEntity>>

    @Query("SELECT * FROM notes WHERE id == :id")
    suspend fun getNoteById(id: Int): NoteDbEntity?

    @Insert
    suspend fun createNote(noteDbEntity: NoteDbEntity)

    @Update
    suspend fun updateNote(noteDbEntity: NoteDbEntity)

    @Delete
    suspend fun deleteNote(noteDbEntity: NoteDbEntity)
}