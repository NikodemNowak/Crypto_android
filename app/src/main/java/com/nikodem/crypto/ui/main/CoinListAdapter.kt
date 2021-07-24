package com.nikodem.crypto.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikodem.crypto.databinding.CoinListItemBinding
import com.nikodem.crypto.services.Coin

class CoinListAdapter(
    private val onClick: (Coin) -> Unit
) : ListAdapter<Coin, CoinListAdapter.CoinViewHolder>(CoinDiffCallback) {

    inner class CoinViewHolder(
        private val binding: CoinListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick.invoke(binding.item!!)
            }
        }

        fun bind(coin: Coin): View {
            binding.item = coin
            binding.changeBoolean = isValueIncreased(coin.change.toDouble())
            return binding.root
        }

        private fun isValueIncreased(double: Double) = double >= 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding =
            CoinListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        holder.bind(coin)
    }
}

object CoinDiffCallback : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.uuid == newItem.uuid
    }
}
