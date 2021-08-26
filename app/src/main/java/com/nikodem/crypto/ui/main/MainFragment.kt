package com.nikodem.crypto.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentMainBinding
import com.nikodem.crypto.ui.favorites.FavoritesFragment
import com.nikodem.crypto.ui.main.crypto.CryptoFragment
import com.nikodem.crypto.utils.BaseFragment

class MainFragment :
    BaseFragment<MainFragmentViewState, MainFragmentViewModel, FragmentMainBinding>(
        contentLayout = R.layout.fragment_main,
        viewModelKClass = MainFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
            setCustomView(R.layout.toolbar_with_settings_button)
        }

        val settingsButton: ImageButton = requireActivity().findViewById(R.id.settingsButton)

        settingsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment())
        }

        with(binding) {
            val pagerAdapter = ScreenSlidePagerAdapter(this@MainFragment)
            viewPager.adapter = pagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { _, _ ->

            }.attach()

            tabLayout.getTabAt(0)?.setIcon(R.drawable.list_icon)
            tabLayout.getTabAt(1)?.setIcon(R.drawable.favorite_list_icon)
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUMBER_OF_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> CryptoFragment()
            1 -> FavoritesFragment()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    companion object {
        const val NUMBER_OF_PAGES = 2
    }
}
