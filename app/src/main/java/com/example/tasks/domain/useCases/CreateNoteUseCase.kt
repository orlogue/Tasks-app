package com.example.tasks.domain.useCases

import com.example.tasks.domain.entities.Note
import com.example.tasks.domain.repositories.Repository

class CreateNoteUseCase(private val repository: Repository) {
    suspend fun execute(note: Note) {
        repository.addNote(note)
    }
}