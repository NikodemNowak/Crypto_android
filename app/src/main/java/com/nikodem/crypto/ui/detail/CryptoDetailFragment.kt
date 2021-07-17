package com.nikodem.crypto.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentCryptoDetailBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseFragment

class CryptoDetailFragment :
    BaseFragment<CryptoDetailFragmentViewState, CryptoDetailFragmentViewModel, FragmentCryptoDetailBinding>(
        contentLayout = R.layout.fragment_crypto_detail,
        viewModelKClass = CryptoDetailFragmentViewModel::class
    ) {

    private val args: CryptoDetailFragmentArgs by navArgs()
    private val coin: Coin by lazy { args.coin }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCoin(coin)
    }

    override fun onStateChanged(state: CryptoDetailFragmentViewState) {
        val coin = viewModel.viewState.value!!.coin
        println("coin2: $coin")
        if (!coin.iconUrl.isNullOrEmpty()) {
            GlideToVectorYou
                .init()
                .with(requireContext())
                .withListener(object : GlideToVectorYouListener {
                    override fun onLoadFailed() {

                    }

                    override fun onResourceReady() {
                    }
                })
                .load(Uri.parse(coin.iconUrl), binding.icon);
        }
    }
}