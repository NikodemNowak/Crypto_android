package com.nikodem.crypto.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
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
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        viewModel.setCoin(coin)
    }

    override fun onStateChanged(state: CryptoDetailFragmentViewState) {
        val coin = viewModel.viewState.value!!.coin
//        println("coin2: $coin")
        if (!coin.iconUrl.isNullOrEmpty()) {
            GlideToVectorYou
                .init()
                .with(requireContext())
                .setPlaceHolder(R.drawable.placeholder, R.drawable.placeholder)
                .load(Uri.parse(coin.iconUrl), binding.icon)
        }
        val change = coin.change.toDouble()
        if (change >= 0) {
            binding.arrow.setImageResource(R.drawable.increase)
        } else {
            binding.arrow.setImageResource(R.drawable.decrease)
        }
    }
}