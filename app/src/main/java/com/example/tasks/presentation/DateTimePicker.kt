package com.example.tasks.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.tasks.data.DateTimeConverter
import com.example.tasks.data.Note
import java.util.*

object DateTimePicker {
    fun pickDateTime(
        note: Note?,
        context: Context,
        handleFunction: (pickedDateTime: Calendar) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        note?.let {
            val offsetDT = DateTimeConverter.toOffsetDateTime(note.date)
            offsetDT?.let {
                val date = Date.from(offsetDT.toInstant())
                calendar.time = date
            }
        }

        val startYear = calendar.get(Calendar.YEAR)
        val startMonth = calendar.get(Calendar.MONTH)
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)
        val startHour = calendar.get(Calendar.HOUR_OF_DAY)
        val startMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                handleFunction(pickedDateTime)
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }
}