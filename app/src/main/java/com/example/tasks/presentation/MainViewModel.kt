package com.example.tasks.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.tasks.data.*
import com.example.tasks.domain.MainRepository.repository
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



    fun initDatabase() {
        viewModelScope.launch {
            repository.initDatabase()
        }
    }

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
        runBlocking {
            val job = viewModelScope.launch {
                renameListUseCase.execute(list)
            }
            job.join()
            getLists()
        }
    }

    fun deleteList(listId: Int) {
        Log.d("ID", listId.toString())
        viewModelScope.launch(Dispatchers.IO) {
            deleteListUseCase.execute(listId)
            withContext(Dispatchers.Main) {
                getLists()
            }
        }
//        runBlocking {
//            val job = viewModelScope.launch {
//                deleteListUseCase.execute(listId)
//            }
//            job.join()
//            getLists()
//        }
    }

    fun getLists() {
        val list = getListUseCase.execute()
        lists = list.asLiveData()
        Log.d("MyLog", list.asLiveData().value.toString())
    }

    fun createNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            createNoteUseCase.execute(note)
            withContext(Dispatchers.Main) {
                getNotesList()
            }
        }
    }

    fun editNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            editNoteUseCase.execute(note)
//            delay(100)
            withContext(Dispatchers.Main) {
                getNotesList()
                Log.d("Updated", note.toString())
            }
        }
    }

    fun getNotesList() {
        val list = getNotesUseCase.execute()

        notes = list.asLiveData()
        Log.d("MyLog", notes.value.toString())
    }
}