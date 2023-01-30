package com.example.tasks.data

import com.example.tasks.domain.Repository

class RepositoryImplementation : Repository {
    override fun createList(list: NotesList) {
        TODO("Not yet implemented")
    }

    override fun renameList(list: NotesList) {
        TODO("Not yet implemented")
    }

    override fun deleteList(listId: Int) {
        TODO("Not yet implemented")
    }

    override fun getLists(): List<NotesList> {
        TODO("Not yet implemented")
    }
    
    override fun getNote(id: Int): Note {
        TODO("Not yet implemented")
    }

    override fun addNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun editNote(note: Note): Note {
        TODO("Not yet implemented")
    }

    override fun deleteNote(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getNotesList(listId: Int): List<Note> {
        TODO("Not yet implemented")
    }
}