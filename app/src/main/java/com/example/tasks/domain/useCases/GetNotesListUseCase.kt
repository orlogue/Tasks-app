package com.example.tasks.domain.useCases

import com.example.tasks.data.Note
import com.example.tasks.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetNotesListUseCase(private val repository: Repository) {
    fun execute(): Flow<List<Note>> {
        return repository.getNotesList()
    }
}