package com.example.tasks.presentation.ui

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.Note
import com.example.tasks.databinding.FragmentNoteBinding
import com.example.tasks.presentation.MainViewModel


class NotesRecyclerViewAdapter(
    private val values: List<Note>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()

        if (item.isCompleted) {
            holder.checkBox.isChecked = true
            holder.titleView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.checkBox.isChecked = false
            holder.titleView.paintFlags = 0
        }

        if (item.isFavorite) {
            holder.makeFavorite.setImageResource(R.drawable.baseline_star_24)
        } else {
            holder.makeFavorite.setImageResource(R.drawable.baseline_star_border_24)
        }

        holder.checkBox.setOnClickListener {
            Log.d("checked", holder.checkBox.isChecked.toString())
            if (holder.checkBox.isChecked) {
                holder.titleView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                item.isCompleted = true
            } else {
                holder.titleView.paintFlags = 0
                item.isCompleted = false
            }
            Log.d("checked", item.toString())
            viewModel.editNote(item)
        }

        holder.makeFavorite.setOnClickListener {
            item.isFavorite = !item.isFavorite
            viewModel.editNote(item)
        }

        holder.noteBox.setOnClickListener {
            val activity = it.context as? AppCompatActivity
            activity?.supportFragmentManager!!
                .beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.container,
                    SingleNoteFragment.newInstance(item),
                    "singleNoteFragment"
                )
                .commit()
        }

        holder.titleView.text = item.title
        if (item.description.isNotEmpty()) {
            holder.descriptionView.text = item.description
            holder.descriptionView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val titleView: TextView = binding.noteTitle
        val descriptionView: TextView = binding.noteDescription

        //        val check: RadioButton = binding.check
        val checkBox: CheckBox = binding.checkBox
        val makeFavorite: ImageButton = binding.makeFavorite2
        val noteBox: ConstraintLayout = binding.noteBox


        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'" + descriptionView + "'"
        }
    }
}