package com.example.tasks.domain.useCases

import com.example.tasks.data.NotesList
import com.example.tasks.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetListsUseCase(private val repository: Repository) {
    fun execute() : Flow<List<NotesList>> {
        return repository.getLists()
    }
}