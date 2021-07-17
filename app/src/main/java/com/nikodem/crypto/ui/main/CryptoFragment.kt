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

    lateinit var coinListAdapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinListAdapter = CoinListAdapter {
            navigateToDetails(it)
        }
        with(binding) {
            coinListRecyclerView.apply {
                adapter = coinListAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.loadCryptoData()
    }

    override fun onStateChanged(state: CryptoFragmentViewState) {
        super.onStateChanged(state)

        coinListAdapter.apply {
            setItems(state.coins)
            notifyDataSetChanged() // DiffUtil
        }
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
}