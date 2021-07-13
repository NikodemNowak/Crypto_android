package com.nikodem.crypto.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentCryptoDetailBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseFragment

class CryptoDetailFragment :
    BaseFragment<CryptoDetailFragmentViewState, CryptoDetailFragmentViewModel, FragmentCryptoDetailBinding>(
        contentLayout = R.layout.fragment_crypto_detail,
        viewModelKClass = CryptoDetailFragmentViewModel::class
    ) {

    val args: CryptoDetailFragmentArgs by navArgs()
    val coin: Coin by lazy { args.coin }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.text = coin.name
        binding.symbol.text = coin.symbol
        binding.price.text = coin.price
    }
}