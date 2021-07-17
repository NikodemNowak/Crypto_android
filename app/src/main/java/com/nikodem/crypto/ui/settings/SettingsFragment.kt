package com.nikodem.crypto.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentSettingsBinding
import com.nikodem.crypto.utils.BaseFragment

class SettingsFragment :
    BaseFragment<SettingsFragmentViewState, SettingsFragmentViewModel, FragmentSettingsBinding>(
        contentLayout = R.layout.fragment_settings,
        viewModelKClass = SettingsFragmentViewModel::class
    ) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        setHasOptionsMenu(true)
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
}