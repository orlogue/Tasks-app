package com.example.tasks.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasks.data.db.dao.NotesDao
import com.example.tasks.data.db.models.NoteDbEntity
import com.example.tasks.data.db.models.NotesListDbEntity

@Database(
    version = 1,
    entities = [
        NotesListDbEntity::class,
        NoteDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNotesListDao(): NotesDao
}