package com.example.tasks.domain.useCases

import com.example.tasks.domain.entities.Note
import com.example.tasks.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow

class GetNotesListUseCase(private val repository: Repository) {
    fun execute(): Flow<List<Note>> {
        return repository.getNotesList()
    }
}