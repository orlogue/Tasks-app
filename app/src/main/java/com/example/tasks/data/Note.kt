package com.example.tasks.data

data class Note(
    var listId: Int,
    var title: String,
    var description: String,
    val date: String,
    var isFavorite : Boolean = false,
    var isCompleted : Boolean = false,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}