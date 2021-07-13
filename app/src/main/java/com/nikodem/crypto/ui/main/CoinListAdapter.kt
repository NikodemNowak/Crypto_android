package com.nikodem.crypto.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikodem.crypto.databinding.CoinListItemBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.setOnClick

class CoinListAdapter(
    private val onClick: (Coin) -> Unit
) : RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {

    private val items: MutableList<Coin> = mutableListOf()

    fun setItems(list: List<Coin>) {
        items.clear()
        items.addAll(list)
    }

    inner class CoinViewHolder(
        private val binding: CoinListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin): View {
            binding.item = coin
            return binding.root
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding =
            CoinListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = items[position]
        holder.bind(coin).setOnClick {
            onClick.invoke(coin)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
