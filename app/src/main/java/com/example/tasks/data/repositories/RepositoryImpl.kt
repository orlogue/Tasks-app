package com.example.tasks.data.repositories

import com.example.tasks.data.db.dao.NotesDao
import com.example.tasks.data.entities.NoteDbEntity
import com.example.tasks.data.entities.NotesListDbEntity
import com.example.tasks.domain.entities.Note
import com.example.tasks.domain.entities.NotesList
import com.example.tasks.domain.repositories.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val notesDao: NotesDao,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {
    override suspend fun createList(list: NotesList) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.createList(NotesListDbEntity.toNotesListDbEntity(list))
        }
    }

    override suspend fun renameList(list: NotesList) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.renameList(NotesListDbEntity.toUpdateNotesListDbEntity(list))
        }
    }

    override suspend fun deleteList(listId: Int) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.deleteList(listId)
        }
    }

    override fun getLists(): Flow<List<NotesList>> {
        return notesDao.getLists().map { it.map { it.toNotesList() } }
    }

    override suspend fun addNote(note: Note) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.createNote(NoteDbEntity.toNoteDbEntity(note))
        }
    }

    override suspend fun editNote(note: Note) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.updateNote(NoteDbEntity.toUpdateNoteDbEntity(note))
        }
    }

    override suspend fun deleteNote(noteId: Int) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.deleteNote(noteId)
        }
    }

    override suspend fun deleteCompletedNotes(listId: Int) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.deleteCompletedNotes(listId)
        }
    }

    override fun getNotesList(): Flow<List<Note>> {
        return notesDao.getNotes().map { it.map { it.toNote() } }
    }
}