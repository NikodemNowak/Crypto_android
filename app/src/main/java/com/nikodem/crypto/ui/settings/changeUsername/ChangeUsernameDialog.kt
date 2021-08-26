package com.nikodem.crypto.ui.settings.changeUsername

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.nikodem.crypto.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeUsernameDialog : DialogFragment() {
    private val viewModel: ChangeUsernameDialogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_change_username, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = viewModel.viewState.value!!.username
        val editText = view.findViewById<EditText>(R.id.editText)
        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)

        with(editText) {
            setText(username)
            doOnTextChanged { text, _, _, _ ->
                checkUsernameField()
                viewModel.setUsername(text.toString())
            }
        }

        checkUsernameField()

        view.findViewById<MaterialButton>(R.id.cancelButton).setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {
            viewModel.saveUsername()
            dismiss()
            Toast.makeText(requireContext(), "Username saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUsernameField() {
        val username = viewModel.viewState.value!!.username
        val editText = view?.findViewById<EditText>(R.id.editText)
        val saveButton = view?.findViewById<MaterialButton>(R.id.saveButton)

        saveButton?.isEnabled = username != editText?.text.toString()
    }

    companion object {
        const val TAG = "CHANGE_USERNAME_DIALOG_TAG"
    }
}