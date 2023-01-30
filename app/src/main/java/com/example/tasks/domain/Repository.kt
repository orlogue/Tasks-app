package com.example.tasks.domain

import com.example.tasks.data.Note
import com.example.tasks.data.NotesList

interface Repository {
    fun createList(list: NotesList)
    fun renameList(list: NotesList)
    fun deleteList(listId: Int)
    fun getLists(): List<NotesList>

    fun getNote(id: Int): Note?
    fun addNote(note: Note)
    fun editNote(note: Note)
    fun deleteNote(id: Int)
    fun getNotesList(listId: Int): List<Note>
}