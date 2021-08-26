package com.nikodem.crypto.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentWelcomeBinding
import com.nikodem.crypto.utils.BaseFragment


class WelcomeFragment :
    BaseFragment<WelcomeFragmentViewState, WelcomeFragmentViewModel, FragmentWelcomeBinding>(
        contentLayout = R.layout.fragment_welcome,
        viewModelKClass = WelcomeFragmentViewModel::class
    ) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setCustomView(R.layout.toolbar_default)
        }

        viewModel.navigateToCryptoFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment())
        }

        viewModel.isUsernameAlreadyGiven()

        binding.username.doOnTextChanged { text, _, _, _ ->
            viewModel.setUsername(text.toString())
        }
    }
}