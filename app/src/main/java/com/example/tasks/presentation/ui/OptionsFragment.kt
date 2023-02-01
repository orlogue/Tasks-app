package com.example.tasks.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.tasks.data.NotesList
import com.example.tasks.databinding.FragmentOptionsBinding
import com.example.tasks.presentation.MainViewModel

class OptionsFragment : DialogFragment() {
    private lateinit var binding: FragmentOptionsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var listId = 1
    private var listName = ""


    override fun onStart() {
        super.onStart()
        setWindowParams()

        arguments?.let {
            listId = it.getInt(LIST_ID)
            listName = it.getString(LIST_NAME).toString()
        }
    }

    private fun setWindowParams() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater)
        dialog?.setCanceledOnTouchOutside(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteButtonListener()
    }

    private fun deleteButtonListener() {
        binding.deleteListButton.setOnClickListener {
            viewModel.deleteList(listId)
            dismissNow()
        }
    }

    companion object {
        const val LIST_ID = "list-id"
        const val LIST_NAME = "list-name"


        @JvmStatic
        fun newInstance(listId: Int, listName: String) =
            NewNoteFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_ID, listId)
                    putString(LIST_NAME, listName)
                }
            }
    }
}