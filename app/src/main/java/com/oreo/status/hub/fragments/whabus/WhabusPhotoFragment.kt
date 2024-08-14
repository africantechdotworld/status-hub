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
import com.oreo.status.hub.adapters.PhotoAdapter // Import your RecyclerView adapter here
import com.oreo.status.hub.adapters.StatusAdapter

class WhabusPhotoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter // Replace YourAdapter with the name of your RecyclerView adapter class

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
        adapter = PhotoAdapter(uriList) // Initialize your RecyclerView adapter here
        recyclerView.adapter = adapter

        return view
    }
}