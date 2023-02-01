package com.example.tasks.domain

import com.example.tasks.data.Note
import com.example.tasks.data.NotesList
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun createList(list: NotesList)
    suspend fun renameList(list: NotesList)
    suspend fun deleteList(listId: Int)
    fun getLists(): Flow<List<NotesList>>

    suspend fun addNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun deleteNote(noteId: Int)
    suspend fun deleteCompletedNotes(listId: Int)
    fun getNotesList(): Flow<List<Note>>
}