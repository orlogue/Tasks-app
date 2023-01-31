package com.example.tasks.domain

import com.example.tasks.data.Note
import com.example.tasks.data.NotesList
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun initDatabase()
    suspend fun createList(list: NotesList)
    suspend fun renameList(list: NotesList)
    suspend fun deleteList(list: NotesList)
    fun getLists(): Flow<List<NotesList>>

    suspend fun getNote(id: Int): Note?
    suspend fun addNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getNotesList(): Flow<List<Note>>
}