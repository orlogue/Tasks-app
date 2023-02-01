package com.example.tasks.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tasks.data.NotesList

class PagerAdapter(
    parentFragment: FragmentActivity,
    private val notesList: List<NotesList>
) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = NotesFragment()

        fragment.arguments = Bundle().apply {
            putInt(LIST_ID, notesList[position].id)
        }
        return fragment
    }

    companion object {
        const val LIST_ID = "list-id"
    }
}