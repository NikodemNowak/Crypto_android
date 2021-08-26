package com.nikodem.crypto.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentFavoritesBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.ui.main.MainFragmentDirections
import com.nikodem.crypto.ui.main.crypto.CoinListAdapter
import com.nikodem.crypto.utils.BaseFragment

class FavoritesFragment :
    BaseFragment<FavoritesFragmentViewState, FavoritesFragmentViewModel, FragmentFavoritesBinding>(
        R.layout.fragment_favorites,
        FavoritesFragmentViewModel::class
    ) {

    private var coinListAdapter: CoinListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshingFinishedEvent.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        coinListAdapter = CoinListAdapter {
            navigateToDetails(it)
        }
        with(binding) {
            favoriteCoinsRecyclerView.apply {
                adapter = coinListAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadCryptoData(useCacheDataIfPossible = false)
            }
        }

        viewModel.loadCryptoData(useCacheDataIfPossible = true)
    }

    private fun navigateToDetails(coin: Coin) {
        val directions =
            MainFragmentDirections.actionMainFragmentToCryptoDetailFragment(coin)
        findNavController().navigate(directions)
    }

    override fun onStateChanged(state: FavoritesFragmentViewState) {
        super.onStateChanged(state)
        coinListAdapter?.submitList(state.coins.filter { it.isFavorite })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coinListAdapter = null
    }
}