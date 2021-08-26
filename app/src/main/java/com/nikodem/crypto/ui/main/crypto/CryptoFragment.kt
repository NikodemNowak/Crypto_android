package com.nikodem.crypto.ui.main.crypto

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentCryptoBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.ui.main.MainFragmentDirections
import com.nikodem.crypto.utils.BaseFragment

class CryptoFragment :
    BaseFragment<CryptoFragmentViewState, CryptoFragmentViewModel, FragmentCryptoBinding>(
        R.layout.fragment_crypto,
        CryptoFragmentViewModel::class
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
            coinListRecyclerView.apply {
                adapter = coinListAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadCryptoData(useCacheDataIfPossible = false)
            }
        }

        viewModel.loadCryptoData(useCacheDataIfPossible = true)
    }

    override fun onStateChanged(state: CryptoFragmentViewState) {
        super.onStateChanged(state)
        coinListAdapter?.submitList(state.coins)
    }

    private fun navigateToDetails(coin: Coin) {
        val directions =
            MainFragmentDirections.actionMainFragmentToCryptoDetailFragment(coin)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coinListAdapter = null
    }
}