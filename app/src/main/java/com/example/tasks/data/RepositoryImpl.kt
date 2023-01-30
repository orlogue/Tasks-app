package com.example.tasks.data

import com.example.tasks.domain.Repository

class RepositoryImpl : Repository {
    private val lists: MutableList<NotesList> = mutableListOf()
    private val notes: MutableList<Note> = mutableListOf()


    override fun createList(list: NotesList) {
        lists.add(list)
    }

    override fun renameList(list: NotesList) {
        lists.find { it.id == list.id }?.name = list.name
    }

    override fun deleteList(listId: Int) {
        lists.removeIf { it.id == listId }
    }

    override fun getLists(): List<NotesList> {
        return lists.toList()
    }

    override fun getNote(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override fun addNote(note: Note) {
        notes.add(note)
    }

    override fun editNote(note: Note) {
        notes.find { it == note }?.apply {
            title = note.title
            description = note.description
            listId = note.listId
            isFavorite = note.isFavorite
            isCompleted = note.isCompleted
        }
    }

    override fun deleteNote(id: Int) {
        notes.removeIf { it.id == id }
    }

    override fun getNotesList(listId: Int): List<Note> {
        return notes.filter { it.listId == listId}
    }
}