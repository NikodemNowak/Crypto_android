package com.nikodem.crypto.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.transition.CrossfadeTransition
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentCryptoDetailBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseFragment
import timber.log.Timber

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

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setCustomView(R.layout.toolbar_detail_fragment)
        }

        val favoriteButton = requireActivity().findViewById<ImageButton>(R.id.favoriteButton)
        val alertButton = requireActivity().findViewById<ImageButton>(R.id.alertButton)

        if (viewModel.isCoinFavorite(coin.name)) {
            favoriteButton.setImageResource(R.drawable.star_full)
        } else {
            favoriteButton.setImageResource(R.drawable.star_border)
        }

        favoriteButton.setOnClickListener {
            val isFavoriteCoin = viewModel.changeFavoriteCoin(coin.name)
            if (isFavoriteCoin) {
                favoriteButton.setImageResource(R.drawable.star_full)
            } else {
                favoriteButton.setImageResource(R.drawable.star_border)
            }
            Timber.d(viewModel.getFavoriteCoins().toString())
        }

        val coin = viewModel.viewState.value!!
        alertButton.setOnClickListener {
            navigateToAlert(coin.name, coin.uuid)
        }

        binding.showDetailsButton.setOnClickListener {
            viewModel.loadDetailCryptoData(coin.uuid)
        }
    }

    @ExperimentalCoilApi
    override fun onStateChanged(state: CryptoDetailFragmentViewState) {
        val coinChange = viewModel.viewState.value!!.change
        val iconUrl = viewModel.viewState.value!!.iconUrl
        val imageView: ImageView = binding.icon

        if (!iconUrl.isNullOrEmpty()) {
            val request = ImageRequest.Builder(requireContext())
                .data(iconUrl)
                .error(R.drawable.placeholder)
                .transition(CrossfadeTransition())
                .target(imageView)
                .build()

            val imageLoader = ImageLoader.Builder(requireContext())
                .componentRegistry {
                    add(SvgDecoder(requireContext()))
                }
                .build()

            imageLoader.enqueue(request)
        }
        val change = coinChange.toDouble()
        binding.arrow.setImageResource(if (change >= 0) R.drawable.increase else R.drawable.decrease)

        if (!state.isButtonVisible) {
            binding.details.visibility = View.VISIBLE
            binding.showDetailsButton.visibility = View.GONE
        }

        binding.supplyDataLayout.visibility =
            if (!state.isSupplySectionVisible) View.GONE else View.VISIBLE
    }

    private fun navigateToAlert(coinName: String, coinUuid: String) {
        val directions =
            CryptoDetailFragmentDirections.actionCryptoDetailFragmentToCryptoAlertFragment(
                coinName,
                coinUuid
            )
        findNavController().navigate(directions)
    }
}