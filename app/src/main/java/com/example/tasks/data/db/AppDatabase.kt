package com.example.tasks.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.tasks.data.DateTimeConverter
import com.example.tasks.data.db.dao.NotesDao
import com.example.tasks.data.db.models.NoteDbEntity
import com.example.tasks.data.db.models.NotesListDbEntity

@Database(
    version = 1,
    entities = [
        NotesListDbEntity::class,
        NoteDbEntity::class
    ]
//    autoMigrations = [
//        AutoMigration(
//            from = 1,
//            to = 2
//        )
//    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}