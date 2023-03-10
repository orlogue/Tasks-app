package com.example.tasks.domain.useCases

import com.example.tasks.domain.repositories.Repository

class DeleteCompletedNotesUseCase(private val repository: Repository) {
    suspend fun execute(listId: Int) {
        return repository.deleteCompletedNotes(listId)
    }
}