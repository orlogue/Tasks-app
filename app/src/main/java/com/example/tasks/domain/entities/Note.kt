package com.example.tasks.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var listId: Int,
    var title: String,
    var description: String,
    var date: String? = null,
    var isFavorite : Boolean = false,
    var isCompleted : Boolean = false,
    val id: Int = UNDEFINED_ID
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = -1
    }
}