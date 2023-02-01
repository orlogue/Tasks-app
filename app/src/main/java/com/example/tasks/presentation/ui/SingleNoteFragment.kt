package com.example.tasks.presentation.ui

import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tasks.R
import com.example.tasks.data.Note
import com.example.tasks.databinding.FragmentSingleNoteBinding
import com.example.tasks.presentation.MainViewModel

@Suppress("DEPRECATION")
class SingleNoteFragment : Fragment() {
    private lateinit var binding: FragmentSingleNoteBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getParcelable(NOTE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleNoteBinding.inflate(inflater)
        inflateViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButtonListener()
        setupListeners()
        deleteButtonListener()
    }

    private fun inflateViews() {
        binding.apply {
            singleNoteTitle.setText(note.title)
            singleNoteDescription.setText(note.description)

            if (note.isFavorite) {
                favoriteButton.setImageResource(R.drawable.baseline_star_24)
            }

            if (note.isCompleted) {
                singleNoteTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                markCompletedButton.setText(R.string.mark_uncompleted)
            } else {
                singleNoteTitle.paintFlags = 0
                markCompletedButton.setText(R.string.mark_completed)
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            singleNoteTitle.doAfterTextChanged {
                note.title = singleNoteTitle.text.toString()
            }
            singleNoteDescription.doAfterTextChanged {
                note.description = singleNoteDescription.text.toString()
            }
            favoriteButton.setOnClickListener {
                if (note.isFavorite) {
                    favoriteButton.setImageResource(R.drawable.baseline_star_border_24)
                } else {
                    favoriteButton.setImageResource(R.drawable.baseline_star_24)
                }
                note.isFavorite = !note.isFavorite
            }
            markCompletedButton.setOnClickListener {
                if (!note.isCompleted) {
                    note.isCompleted = true
                    viewModel.editNote(note)
                    closeFragment()
                } else {
                    note.isCompleted = false
                    markCompletedButton.setText(R.string.mark_completed)
                    singleNoteTitle.paintFlags = 0
                }
            }
        }
    }

    private fun deleteButtonListener() {
        binding.deleteNoteButton.setOnClickListener {
            viewModel.deleteNote(note.id)
            closeFragment()
        }
    }


    private fun backButtonListener() {
        binding.backButton.setOnClickListener {
            viewModel.editNote(note)
            closeFragment()
        }
    }

    private fun closeFragment() {
        val manager = activity?.supportFragmentManager!!
        manager.beginTransaction().remove(this).commit()
        manager.popBackStack()
    }


    companion object {
        const val NOTE = "note"
        @JvmStatic
        fun newInstance(note: Note) =
            SingleNoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NOTE, note)
                }
            }
    }
}