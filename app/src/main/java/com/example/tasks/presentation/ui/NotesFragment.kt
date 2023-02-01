package com.example.tasks.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tasks.data.Note
import com.example.tasks.databinding.FragmentNoteListBinding
import com.example.tasks.presentation.MainViewModel

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var listId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listId = it.getInt(LIST_ID)
        }
        Log.d("MyLog3", listId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater)
        Log.d("MyLog2", listId.toString())
        viewModel.getNotesList()
        Log.d("MyLog2", viewModel.notes.toString())

        binding.list.layoutManager = LinearLayoutManager(context)

        viewModel.notes.observe(viewLifecycleOwner) {
            if (listId == 1) {
                binding.list.adapter = NotesRecyclerViewAdapter(it
                    .filter { it.isFavorite }
                    .sortedBy { it.isCompleted },
                    viewModel
                )
            } else {
                binding.list.adapter = NotesRecyclerViewAdapter(it
                    .filter { it.listId == listId }
                    .sortedBy { it.isCompleted },
                    viewModel
                )
            }
        }
        return binding.root
    }

    companion object {
        const val LIST_ID = "list-id"

        @JvmStatic
        fun newInstance(listId: Int) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_ID, listId)
                }
            }
    }
}