package com.example.tasks.data.db.dao

import androidx.room.*
import com.example.tasks.data.db.models.NotesListDbEntity

@Dao
interface NotesDao {
    @Query("SELECT * FROM lists")
    suspend fun getLists(): List<NotesListDbEntity>

    @Insert
    suspend fun createList(notesListDbEntity: NotesListDbEntity)

    @Update
    suspend fun renameList(notesListDbEntity: NotesListDbEntity)

    @Delete
    suspend fun deleteList(notesListDbEntity: NotesListDbEntity)
}