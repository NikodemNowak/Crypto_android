package com.nikodem.crypto.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentSettingsBinding
import com.nikodem.crypto.ui.settings.changeUsername.ChangeUsernameDialog
import com.nikodem.crypto.utils.BaseFragment

class SettingsFragment :
    BaseFragment<SettingsFragmentViewState, SettingsFragmentViewModel, FragmentSettingsBinding>(
        contentLayout = R.layout.fragment_settings,
        viewModelKClass = SettingsFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.darkMode.isChecked = viewModel.viewState.value!!.darkMode

        binding.darkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.darkModeOn()
            } else {
                viewModel.darkModeOff()
            }
        }

        binding.openChangeUsernameDialog.setOnClickListener {
            ChangeUsernameDialog().show(childFragmentManager, ChangeUsernameDialog.TAG)
        }
    }

    override fun onStateChanged(state: SettingsFragmentViewState) {
        super.onStateChanged(state)
        if (viewModel.viewState.value!!.darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}