package com.oreo.status.hub.fragments.whabus

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oreo.status.hub.R
import com.oreo.status.hub.adapters.StatusAdapter
import com.oreo.status.hub.adapters.VideoAdapter // Import your RecyclerView adapter here

class WhabusVideoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter // Replace YourAdapter with the name of your RecyclerView adapter class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_child, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val uriList = mutableListOf<Uri>()
        adapter = VideoAdapter(uriList) // Initialize your RecyclerView adapter here
        recyclerView.adapter = adapter

        return view
    }
}