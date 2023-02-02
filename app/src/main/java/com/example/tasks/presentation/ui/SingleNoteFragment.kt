package com.example.tasks.presentation.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.tasks.R
import com.example.tasks.data.DateTimeConverter
import com.example.tasks.data.Note
import com.example.tasks.databinding.FragmentSingleNoteBinding
import com.example.tasks.presentation.DateTimePicker
import com.example.tasks.presentation.MainViewModel
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

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

            if (note.date != null) {
                singleNoteDate.text = formatDate()
                val toOffset = DateTimeConverter.toOffsetDateTime(note.date)!!
                if (toOffset.isBefore(OffsetDateTime.now())) {
                    singleNoteDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    singleNoteDate.setBackgroundResource(R.drawable.date_shape_red)
                }
            }
        }
    }

    private fun formatDate(): String {
        val parsedDate = OffsetDateTime.parse(note.date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        return parsedDate.format(DateTimeFormatter.ofPattern("d MMM yyyy HH:mm"))
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
            singleNoteDate.setOnClickListener {
                DateTimePicker.pickDateTime(note, requireContext(), ::handlePickedDateTime)
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
        manager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .remove(this)
            .commit()
        manager.popBackStack()
    }

    private fun handlePickedDateTime(pickedDateTime: Calendar) {
        val date = pickedDateTime.time
        val zoneOffset = ZoneOffset.ofHours(10)
        var localDate = date.toInstant().atOffset(ZoneOffset.UTC)
        localDate = localDate.withOffsetSameInstant(zoneOffset);
        note.date = DateTimeConverter.fromOffsetDateTime(localDate)
        binding.apply {
            singleNoteDate.text = formatDate()
            if (localDate.isBefore(OffsetDateTime.now())) {
                singleNoteDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                singleNoteDate.setBackgroundResource(R.drawable.date_shape_red)
            } else {
                singleNoteDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                singleNoteDate.setBackgroundResource(R.drawable.date_shape)
            }
        }
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