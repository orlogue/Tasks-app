package com.example.tasks.data

import com.example.tasks.data.db.dao.NotesDao
import com.example.tasks.data.db.models.NoteDbEntity
import com.example.tasks.data.db.models.NotesListDbEntity
import com.example.tasks.domain.Repository
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
        val list: Flow<List<NotesList>>
        list = notesDao.getLists().map { it.map { it.toNotesList() } }
        return list
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
        val list: Flow<List<Note>>
        list = notesDao.getNotes().map { it.map { it.toNote() } }
        retqurn list
    }
}