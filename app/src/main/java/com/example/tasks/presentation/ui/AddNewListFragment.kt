package com.example.tasks.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.databinding.FragmentAddNewListBinding
import com.example.tasks.presentation.MainViewModel

class AddNewListFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentAddNewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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

        binding.backButton.setOnClickListener {
            val manager = activity?.supportFragmentManager!!
            manager.beginTransaction().remove(this).commit()
            manager.popBackStack()
        }
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