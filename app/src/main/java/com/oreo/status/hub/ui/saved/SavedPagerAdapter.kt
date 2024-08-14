package com.oreo.status.hub.ui.saved

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oreo.status.hub.fragments.whabus.WhabusStatusFragment
import com.oreo.status.hub.fragments.whabus.WhabusVideoFragment
import com.oreo.status.hub.fragments.whabus.WhabusPhotoFragment


class SavedPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3 // Number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        // Return the fragment instance based on position
        return when (position) {
            0 -> WhabusStatusFragment()
            1 -> WhabusVideoFragment()
            2 -> WhabusPhotoFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}