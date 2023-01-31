package com.example.tasks.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tasks.R
import com.example.tasks.data.NotesList
import com.example.tasks.databinding.FragmentAddNewListBinding
import com.example.tasks.presentation.MainViewModel
import kotlinx.coroutines.launch

class AddNewListFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAddNewListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewListBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeEditFocused()

        binding.apply {
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
                createList()
            }
            listTitleInput.setOnClickListener {
                createList()
            }
        }
    }

    private fun createList() {
        viewModel.createList(
            NotesList(NotesList.UNDEFINED_ID, binding.listTitleInput.text.toString())
        )
        Log.d("MyLog", viewModel.lists.value.toString())
        closeFragment()
    }

    private fun closeFragment() {
        val manager = activity?.supportFragmentManager!!
        manager.beginTransaction().remove(this).commit()
        manager.popBackStack()
    }

    private fun makeEditFocused() {
        binding.listTitleInput.requestFocus()
        val inputManager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(binding.listTitleInput, InputMethodManager.SHOW_IMPLICIT)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNewListFragment()
    }
}