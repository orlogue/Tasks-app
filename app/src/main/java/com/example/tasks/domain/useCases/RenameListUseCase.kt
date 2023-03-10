package com.example.tasks.domain.useCases

import com.example.tasks.domain.entities.NotesList
import com.example.tasks.domain.repositories.Repository

class RenameListUseCase(private val repository: Repository) {
    suspend fun execute(list: NotesList) {
        return repository.renameList(list)
    }
}