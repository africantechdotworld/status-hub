package com.oreo.status.hub.fragments.whabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import com.oreo.status.hub.R
import com.oreo.status.hub.adapters.StatusAdapter // Import your RecyclerView adapter here

class WhabusStatusFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatusAdapter // Replace YourAdapter with the name of your RecyclerView adapter class

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
        adapter = StatusAdapter(uriList) // Initialize your RecyclerView adapter here
        recyclerView.adapter = adapter

        return view
    }
}
