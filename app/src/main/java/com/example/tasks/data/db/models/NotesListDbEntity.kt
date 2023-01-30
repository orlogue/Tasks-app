package com.example.tasks.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasks.data.NotesList

@Entity(
    tableName = "lists"
)
data class NotesListDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
) {
    fun toNotesList() : NotesList = NotesList(
        id = id,
        name = name
    )

    companion object {
        fun toNotesListDbEntity(notesList: NotesList) : NotesListDbEntity = NotesListDbEntity(
            id = 0,
            name = notesList.name
        )
    }
}
