package com.example.tasks.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tasks.R
import com.example.tasks.data.NotesList
import com.example.tasks.databinding.FragmentAddNewListBinding
import com.example.tasks.presentation.MainViewModel

class CreateRenameListFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAddNewListBinding
    private var listId = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listId = it.getInt(LIST_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeEditFocused()

        binding.apply {

            if (listId > 1) {
                title.setText(R.string.rename_list)
            }

            backButton.setOnClickListener {
                closeFragment()
            }
            listTitleInput.doAfterTextChanged {
                if (listTitleInput.text.isNotEmpty()) {
                    doneButton.isEnabled = true
                    doneButton.setTextColor(getColor(requireContext(), R.color.blue))
                } else {
                    doneButton.isEnabled = false
                    doneButton.setTextColor(getColor(requireContext(), R.color.gray))
                }
            }
            doneButton.setOnClickListener {
                if (listId > 1)
                    renameList()
                else
                    createList()
            }
        }
    }

    private fun createList() {
        viewModel.createList(
            NotesList(NotesList.UNDEFINED_ID, binding.listTitleInput.text.toString())
        )
        closeFragment()
    }

    private fun renameList() {
        viewModel.renameList(NotesList(listId, binding.listTitleInput.text.toString()))
        closeFragment()
    }

    private fun closeFragment() {
        val manager = activity?.supportFragmentManager!!
        manager.beginTransaction().remove(this).commit()
        manager.popBackStack()
    }

    private fun makeEditFocused() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        binding.listTitleInput.requestFocus()
        inputManager.showSoftInput(binding.listTitleInput, InputMethodManager.SHOW_IMPLICIT)
    }

    companion object {

        const val LIST_ID = "list-id"

        @JvmStatic
        fun newInstance(listId: Int) = CreateRenameListFragment().apply {
            arguments = Bundle().apply {
                putInt(CreateRenameListFragment.LIST_ID, listId)
            }
        }
    }
}