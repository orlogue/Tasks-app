package com.example.tasks.domain.useCases

import com.example.tasks.data.Note
import com.example.tasks.domain.Repository

class CreateNoteUseCase(private val repository: Repository) {
    suspend fun execute(note: Note) {
        repository.addNote(note)
    }
}