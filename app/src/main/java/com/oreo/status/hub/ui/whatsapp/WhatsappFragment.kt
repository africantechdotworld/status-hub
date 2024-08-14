package com.oreo.status.hub.ui.whatsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.oreo.status.hub.databinding.FragmentWhatsappBinding


class WhatsappFragment : Fragment() {

    private var _binding: FragmentWhatsappBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val whatsappViewModel =
            ViewModelProvider(this).get(WhatsappViewModel::class.java)

        _binding = FragmentWhatsappBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.viewPager

        // Set up ViewPager with FragmentPagerAdapter
        val adapter = WhatsappPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Set tab titles here
            when (position) {
                0 -> tab.text = "Status"
                1 -> tab.text = "Videos"
                2 -> tab.text = "Photos"
            }
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}