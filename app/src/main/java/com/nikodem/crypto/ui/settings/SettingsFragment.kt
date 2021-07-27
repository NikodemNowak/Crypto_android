package com.nikodem.crypto.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentSettingsBinding
import com.nikodem.crypto.utils.BaseFragment

class SettingsFragment :
    BaseFragment<SettingsFragmentViewState, SettingsFragmentViewModel, FragmentSettingsBinding>(
        contentLayout = R.layout.fragment_settings,
        viewModelKClass = SettingsFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        setHasOptionsMenu(true)

        binding.darkMode.isChecked = viewModel.viewState.value!!.darkMode

        binding.darkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.darkModeOn()
            } else {
                viewModel.darkModeOff()
            }
        }

        binding.openChangeUsernameDialog.setOnClickListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                Toast.makeText(requireContext(), "text", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
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