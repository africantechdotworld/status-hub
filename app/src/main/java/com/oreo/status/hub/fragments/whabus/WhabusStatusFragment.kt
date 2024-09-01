package com.oreo.status.hub.fragments.whabus

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import android.content.ContentUris
import android.provider.MediaStore
import android.widget.Toast
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Environment
import android.provider.DocumentsContract
import com.oreo.status.hub.R
import androidx.documentfile.provider.DocumentFile
import com.oreo.status.hub.adapters.StatusAdapter // Import your RecyclerView adapter here

class WhabusStatusFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatusAdapter // Replace YourAdapter with the name of your RecyclerView adapter class
    private var savedUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_child, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        //val uriList = mutableListOf<Uri>()
        adapter = StatusAdapter(emptyList()) // Initialize your RecyclerView adapter here
        recyclerView.adapter = adapter

        // Retrieve saved URI from shared preferences
        savedUri = getSavedUri()

        if (savedUri != null) {
            val subDir = constructSubdir(savedUri!!)
            Toast.makeText(requireContext(), subDir.toString(), Toast.LENGTH_SHORT).show()
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("URI", subDir.toString())
            clipboard.setPrimaryClip(clipData)
            val files = getFilesFromDir(subDir)
            // Update the adapter with the retrieved files
            adapter.updateData(files)
            //Toast.makeText(requireContext(), "Saved URI found", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "Number of items: ${files.size}", Toast.LENGTH_SHORT).show()
        }
        else {
                Toast.makeText(requireContext(), "No saved URI found", Toast.LENGTH_SHORT).show()
        }

        return view
    }
    private fun getSavedUri(): Uri? {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedUriString = sharedPreferences.getString("savedUri", null)
        return if (savedUriString != null) {
            Uri.parse(savedUriString)
        } else {
            null
        }

    }
    private fun constructSubdir(uri: Uri): Uri {
        val subFolderPath = "com.whatsapp.w4b/Whatsapp Business/.Shared"
        val rootId = DocumentsContract.getTreeDocumentId(uri)
        return Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsapp%20Business%2FMedia%2F.Statuses")
    }
    private fun getFilesFromDir(uri: Uri): List<Uri> {
        //var filesList = mutableListOf<Uri>()

        /*
        // Specify which columns you need in the result
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        // Filter files only with the desired MIME types, you can customize this as per your requirements
        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} IN (?, ?)"
        val selectionArgs = arrayOf("image/jpeg", "video/mp4")

        // Sort the result by display name
        val sortOrder = "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"

        // Query the content resolver to get files
        requireContext().contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            // Iterate through the cursor and add URIs to the list
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                val displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                val mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE))
                val fileUri = ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), id)
                filesList.add(fileUri)
            }
        }
         */
        val fileList = mutableListOf<Uri>()
        try {
            val docFile = DocumentFile.fromTreeUri(requireContext(), uri)
            if (docFile != null && docFile.isDirectory) {
                val files = docFile.listFiles()
                if (files != null) {
                    for (file in files) {
                        if (!file.isDirectory) { // Only add files, not subdirectories
                            fileList.add(file.uri)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return fileList
    }
}
