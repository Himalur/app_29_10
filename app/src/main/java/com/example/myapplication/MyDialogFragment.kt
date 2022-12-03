package com.example.myapplication

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Вы действительно хотите удалить контакт?")
            .setPositiveButton("Удалить"
            ) { _, id -> (activity as MainActivity?)!!.okClicked(id.toLong()) }
            .setNegativeButton("Отмена"
            ) { _, _ ->
                dialog!!.cancel()
            }

        return builder.create()
    }
}