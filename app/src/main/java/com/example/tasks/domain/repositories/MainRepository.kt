package com.example.tasks.domain.repositories

import android.content.Context
import androidx.room.Room
import com.example.tasks.data.repositories.RepositoryImpl
import com.example.tasks.data.db.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object MainRepository {
    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("tasks_db.db")
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    val repository: RepositoryImpl by lazy {
        RepositoryImpl(database.getNotesDao(), ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}