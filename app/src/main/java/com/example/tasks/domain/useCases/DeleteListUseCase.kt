package com.example.tasks.domain.useCases

import com.example.tasks.data.NotesList
import com.example.tasks.domain.Repository

class DeleteListUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int) {
        return repository.deleteList(listId)
    }
}