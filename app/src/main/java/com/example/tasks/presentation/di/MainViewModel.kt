package com.example.tasks.presentation.di

import android.util.Log
import androidx.lifecycle.*
import com.example.tasks.domain.entities.Note
import com.example.tasks.domain.entities.NotesList
import com.example.tasks.domain.repositories.MainRepository.repository
import com.example.tasks.domain.useCases.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    lateinit var notes: LiveData<List<Note>>
    lateinit var lists: LiveData<List<NotesList>>


    private val createListUseCase = CreateListUseCase(repository)
    private val renameListUseCase = RenameListUseCase(repository)
    private val deleteListUseCase = DeleteListUseCase(repository)
    private val getListUseCase = GetListsUseCase(repository)

    private val createNoteUseCase = CreateNoteUseCase(repository)
    private val editNoteUseCase = EditNoteUseCase(repository)
    private val getNotesUseCase = GetNotesUseCase(repository)
    private val deleteCompletedNotesUseCase = DeleteCompletedNotesUseCase(repository)
    private val deleteNoteUseCase =DeleteNoteUseCase(repository)

    fun createList(list: NotesList) {
        runBlocking {
            val job = viewModelScope.launch {
                createListUseCase.execute(list)
            }
            job.join()
            getLists()
        }
    }

    fun renameList(list: NotesList) {
        viewModelScope.launch(Dispatchers.IO) {
            renameListUseCase.execute(list)
        }
    }

    fun deleteList(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteListUseCase.execute(listId)
        }
    }

    fun getLists() {
        val list = getListUseCase.execute()
        lists = list.asLiveData()
        Log.d("MyLog", list.asLiveData().value.toString())
    }

    fun createNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            createNoteUseCase.execute(note)
        }
    }

    fun editNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            editNoteUseCase.execute(note)
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(noteId)
        }
    }

    fun deleteCompletedNotes(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCompletedNotesUseCase.execute(listId)
        }
    }

    fun getNotesList() {
        val list = getNotesUseCase.execute()

        notes = list.asLiveData()
        Log.d("MyLog", notes.value.toString())
    }
}