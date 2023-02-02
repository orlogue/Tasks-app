package com.example.tasks.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.tasks.R
import com.example.tasks.data.DateTimeConverter
import com.example.tasks.data.Note
import com.example.tasks.databinding.FragmentNewNoteBinding
import com.example.tasks.presentation.DateTimePicker
import com.example.tasks.presentation.MainViewModel
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

class NewNoteFragment : DialogFragment() {
    private lateinit var binding: FragmentNewNoteBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var listId = 2
    private var showDescription = false
    private var isFavorite = false
    private var dateTime: OffsetDateTime? = null


    override fun onStart() {
        super.onStart()
        setWindowParams()

        arguments?.let {
            listId = it.getInt(LIST_ID)
        }
    }

    private fun setWindowParams() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewNoteBinding.inflate(inflater)
        dialog?.setCanceledOnTouchOutside(true)
        makeEditFocused()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDescriptionButtonListener()
        dateTimeButtonListener()
        makeFavoriteButtonListener()
        saveButtonListener()

        if (listId == 1) {
            binding.makeFavorite.setImageResource(R.drawable.baseline_star_36)
        }

        binding.apply {
            titleInput.doAfterTextChanged {
                if (titleInput.text.isNotEmpty()) {
                    saveButton.isEnabled = true
                    saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                } else {
                    saveButton.isEnabled = false
                    saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                }
            }
        }
    }

    private fun addDescriptionButtonListener() {
        binding.apply {
            addDescription.setOnClickListener {
                if (!showDescription)
                    descriptionInput.visibility = View.VISIBLE
                else
                    descriptionInput.visibility = View.GONE
                showDescription = !showDescription
            }
        }
    }

    private fun dateTimeButtonListener() {
        binding.addDatetime.setOnClickListener {
            DateTimePicker.pickDateTime(null, requireContext(), ::handlePickedDateTime)
        }
    }

    private fun handlePickedDateTime(pickedDateTime: Calendar) {
        val date = pickedDateTime.time
        val zoneOffset = ZoneOffset.ofHours(10)
        var localDate = date.toInstant().atOffset(ZoneOffset.UTC)
        localDate = localDate.withOffsetSameInstant(zoneOffset);
        dateTime = localDate
    }

    private fun makeFavoriteButtonListener() {
        binding.apply {
            makeFavorite.setOnClickListener {
                if (!isFavorite) {
                    makeFavorite.setImageResource(R.drawable.baseline_star_36)
                } else {
                    makeFavorite.setImageResource(R.drawable.baseline_star_border_36)
                }
                isFavorite = !isFavorite
            }
        }
    }

    private fun saveButtonListener() {
        binding.apply {
            saveButton.setOnClickListener {
                if (listId == 1) {
                    listId = 2
                    isFavorite = true
                }
                viewModel.createNote(
                    Note(
                        listId,
                        titleInput.text.toString(),
                        descriptionInput.text.toString(),
                        DateTimeConverter.fromOffsetDateTime(dateTime),
                        isFavorite = isFavorite,
                    )
                )
                dismiss()
            }
        }
    }

    private fun makeEditFocused() {
        binding.titleInput.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    companion object {
        const val LIST_ID = "list-id"

        @JvmStatic
        fun newInstance(listId: Int) =
            NewNoteFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_ID, listId)
                }
            }
    }
}