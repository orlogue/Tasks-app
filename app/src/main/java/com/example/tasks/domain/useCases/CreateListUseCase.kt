package com.example.tasks.domain.useCases

import com.example.tasks.data.NotesList
import com.example.tasks.domain.Repository

class CreateListUseCase(private val repository: Repository) {
    suspend fun execute(list: NotesList){
        repository.createList(list)
    }
}