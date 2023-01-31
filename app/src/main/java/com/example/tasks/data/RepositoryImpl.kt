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
    override suspend fun initDatabase() {
        CoroutineScope(ioDispatcher).launch {
//            notesDao.deleteLists()
//            withContext(Dispatchers.Main) {
                notesDao.initDatabase()
//            }
        }
    }

    override suspend fun createList(list: NotesList) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.createList(NotesListDbEntity.toNotesListDbEntity(list))
        }
    }

    override suspend fun renameList(list: NotesList) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.renameList(NotesListDbEntity.toNotesListDbEntity(list))
        }
    }

    override suspend fun deleteList(list: NotesList) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.deleteList(NotesListDbEntity.toNotesListDbEntity(list))
        }
    }

    override fun getLists(): Flow<List<NotesList>> {
        val list: Flow<List<NotesList>>
//        val waitFor = CoroutineScope(ioDispatcher).async {
            list = notesDao.getLists().map { it.map { it.toNotesList() } }
//            return@async list
//        }
        return list
    }

    override suspend fun getNote(id: Int): Note? {
        val waitFor = CoroutineScope(ioDispatcher).async {
            return@async notesDao.getNoteById(id)?.toNote()
        }
        return waitFor.await()
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

    override suspend fun deleteNote(note: Note) {
        CoroutineScope(ioDispatcher).launch {
            notesDao.deleteNote(NoteDbEntity.toNoteDbEntity(note))
        }
    }

    override fun getNotesList(): Flow<List<Note>> {
        val list: Flow<List<Note>>
//        val waitFor = CoroutineScope(ioDispatcher).async {
            list = notesDao.getNotes().map { it.map { it.toNote() } }
//            return@async list
//        }
        return list
    }
}