package com.example.tasks.domain.useCases

import com.example.tasks.data.Note

interface Repository {
    fun getNote(id: Int): Note
    fun addNote(note: Note)
    fun editNote(note: Note): Note
    fun deleteNote(id: Int)
    fun getNoteList(listId: Int): List<Note>
}