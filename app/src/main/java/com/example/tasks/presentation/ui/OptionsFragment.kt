package com.example.tasks.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.tasks.R
import com.example.tasks.databinding.FragmentOptionsBinding
import com.example.tasks.presentation.di.MainViewModel

class OptionsFragment : DialogFragment() {
    private lateinit var binding: FragmentOptionsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var listId = 1

    override fun onStart() {
        super.onStart()
        setWindowParams()
    }

    private fun setWindowParams() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

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
        binding = FragmentOptionsBinding.inflate(inflater)
        dialog?.setCanceledOnTouchOutside(true)

        changeUi()

        viewModel.notes.observe(viewLifecycleOwner) {
            checkCompleted()
        }
        renameListButtonListener()
        deleteListButtonListener()
        deleteCompletedButtonListener()
        return binding.root
    }

    private fun changeUi() {
        if (listId == 1) {
            setButtonDisabled(binding.renameListButton)
            binding.deleteCompletedButton.visibility = View.GONE
        }
        if (listId == 1 || listId == 2) {
            setButtonDisabled(binding.deleteListButton)
        }
    }

    private fun checkCompleted() {
        if (viewModel.notes.value?.any { it.listId == listId && it.isCompleted } == false) {
            setButtonDisabled(binding.deleteCompletedButton)
        }
    }

    private fun setButtonDisabled(button: Button) {
        button.isEnabled = false
        button.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray_background
            )
        )
    }

    private fun renameListButtonListener() {
        binding.renameListButton.setOnClickListener {
            activity?.supportFragmentManager!!
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(
                    (view?.parent as View).id,
                    CreateRenameListFragment.newInstance(listId),
                    "addNewListFragment"
                )
                .commit()
            dismiss()
        }
    }

    private fun deleteListButtonListener() {
        binding.deleteListButton.setOnClickListener {
            viewModel.deleteList(listId)
            dismiss()
        }
    }

    private fun deleteCompletedButtonListener() {
        binding.deleteCompletedButton.setOnClickListener {
            viewModel.deleteCompletedNotes(listId)
            dismiss()
        }
    }

    companion object {
        const val LIST_ID = "list-id"

        @JvmStatic
        fun newInstance(listId: Int) =
            OptionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_ID, listId)
                }
            }
    }
}