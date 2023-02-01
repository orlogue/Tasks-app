package com.example.tasks.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var listId: Int,
    var title: String,
    var description: String,
    val date: String,
    var isFavorite : Boolean = false,
    var isCompleted : Boolean = false,
    val id: Int = UNDEFINED_ID
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = -1
    }
}