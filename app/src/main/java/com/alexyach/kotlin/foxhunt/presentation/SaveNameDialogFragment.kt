package com.alexyach.kotlin.foxhunt.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.alexyach.kotlin.foxhunt.R

class SaveNameDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.app_name))
            .setPositiveButton(getString(R.string.number_of_game_short)) {_, _ -> }
            .create()

    companion object {
        const val TAG_DIALOG = "dialogFragment"
    }
}