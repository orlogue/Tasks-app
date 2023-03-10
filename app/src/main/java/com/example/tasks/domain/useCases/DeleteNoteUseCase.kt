package com.example.tasks.domain.useCases

import com.example.tasks.domain.repositories.Repository

class DeleteNoteUseCase(private val repository: Repository) {
    suspend fun execute(noteId: Int) {
        return repository.deleteNote(noteId)
    }
}