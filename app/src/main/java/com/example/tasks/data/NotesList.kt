package com.example.tasks.data

data class NotesList(
    var id: Int = UNDEFINED_ID,
    var name: String,
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
