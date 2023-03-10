package com.example.tasks.domain.useCases

import com.example.tasks.domain.entities.NotesList
import com.example.tasks.domain.repositories.Repository

class CreateListUseCase(private val repository: Repository) {
    suspend fun execute(list: NotesList){
        repository.createList(list)
    }
}