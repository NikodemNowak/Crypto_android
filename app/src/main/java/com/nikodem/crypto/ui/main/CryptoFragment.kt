package com.nikodem.crypto.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentCryptoBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseFragment

class CryptoFragment :
    BaseFragment<CryptoFragmentViewState, CryptoFragmentViewModel, FragmentCryptoBinding>(
        R.layout.fragment_crypto,
        CryptoFragmentViewModel::class
    ) {

    private var coinListAdapter: CoinListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
            CryptoFragmentDirections.actionCryptoFragmentToCryptoDetailFragment(coin)
        findNavController().navigate(directions)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(CryptoFragmentDirections.actionCryptoFragmentToSettingsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coinListAdapter = null
    }
}