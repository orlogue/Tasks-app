package com.example.tasks.data.db.models

import androidx.room.*
import com.example.tasks.data.Note

@Entity(
    tableName = "notes",
    indices = [Index("list_id")],
    foreignKeys = [
        ForeignKey(
            entity = NotesListDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["list_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class NoteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "list_id") var listId: Int,
    var title: String,
    var description: String,
    val date: String,
    var isFavorite : Boolean = false,
    var isCompleted : Boolean = false,
) {
    fun toNote() : Note = Note(
        id = id,
        listId = listId,
        title = title,
        description = description,
        date = date,
        isFavorite = isFavorite,
        isCompleted = isCompleted
    )

    companion object {
        fun toNoteDbEntity(note: Note) : NoteDbEntity = NoteDbEntity(
            id = 0,
            listId = note.listId,
            title = note.title,
            description = note.description,
            date = note.date,
            isFavorite = note.isFavorite,
            isCompleted = note.isCompleted
        )

        fun toUpdateNoteDbEntity(note: Note) : NoteDbEntity = NoteDbEntity(
            id = note.id,
            listId = note.listId,
            title = note.title,
            description = note.description,
            date = note.date,
            isFavorite = note.isFavorite,
            isCompleted = note.isCompleted
        )
    }
}
