package com.example.tasks.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.tasks.data.*
import com.example.tasks.data.RepositoryImpl
import com.example.tasks.domain.MainRepository.repository
import com.example.tasks.domain.Repository
import com.example.tasks.domain.useCases.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    lateinit var notes: LiveData<List<Note>>
    lateinit var lists: LiveData<List<NotesList>>


    private val createListUseCase = CreateListUseCase(repository)
    private val getListUseCase = GetListsUseCase(repository)
    private val getNotesListUseCase = GetNotesListUseCase(repository)
    private val createNoteUseCase = CreateNoteUseCase(repository)
    private val editNoteUseCase = EditNoteUseCase(repository)

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

    fun getLists() {
//        viewModelScope.launch {
            val list = getListUseCase.execute()
            lists = list.asLiveData()
            Log.d("MyLog", lists.value.toString())
//        }
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
//        viewModelScope.launch {
            val list = getNotesListUseCase.execute()
//            val list2 = list.await()

            notes = list.asLiveData()
            Log.d("MyLog", notes.value.toString())
//        }
    }
}