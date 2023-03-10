package com.example.tasks.data.entities

import androidx.room.*
import com.example.tasks.data.common.DateTimeConverter
import com.example.tasks.domain.entities.Note
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

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
    @field:TypeConverters(DateTimeConverter::class) var date: OffsetDateTime? = null,
    var isFavorite : Boolean = false,
    var isCompleted : Boolean = false,
) {
    fun toNote() : Note = Note(
        id = id,
        listId = listId,
        title = title,
        description = description,
        date = date?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
        isFavorite = isFavorite,
        isCompleted = isCompleted
    )

    companion object {
        fun toNoteDbEntity(note: Note) : NoteDbEntity = NoteDbEntity(
            id = 0,
            listId = note.listId,
            title = note.title,
            description = note.description,
            date = note.date?.let { OffsetDateTime.parse(
                note.date, DateTimeFormatter.ISO_OFFSET_DATE_TIME
            ) },
            isFavorite = note.isFavorite,
            isCompleted = note.isCompleted
        )

        fun toUpdateNoteDbEntity(note: Note) : NoteDbEntity = NoteDbEntity(
            id = note.id,
            listId = note.listId,
            title = note.title,
            description = note.description,
            date = note.date?.let { OffsetDateTime.parse(
                note.date, DateTimeFormatter.ISO_OFFSET_DATE_TIME
            ) },
            isFavorite = note.isFavorite,
            isCompleted = note.isCompleted
        )
    }
}
