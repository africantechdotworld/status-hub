package com.oreo.status.hub.fragments.whabus

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oreo.status.hub.R
import com.oreo.status.hub.adapters.PhotoAdapter
import com.oreo.status.hub.models.Status
import com.oreo.status.hub.utils.Common
import java.io.File
import java.util.*
import java.util.concurrent.Executors


class WhabusPhotoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val imagesList = mutableListOf<Status>()

    private lateinit var imageAdapter: PhotoAdapter
    private lateinit var container: RelativeLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var messageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerview)
        progressBar = view.findViewById(R.id.progressbar)
        container = view.findViewById(R.id.image_container)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        messageTextView = view.findViewById(R.id.message)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
            ContextCompat.getColor(requireActivity(), R.color.teal_700),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark)
        )

        swipeRefreshLayout.setOnRefreshListener { getStatus() }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, Common.GRID_COUNT)
        // Set the RecyclerView layout params programmatically
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        recyclerView.layoutParams = layoutParams

        getStatus()
    }

    private fun getStatus() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> executeNew()
            Common.STATUS_DIRECTORY.exists() -> executeOld()
            else -> {
                messageTextView.visibility = View.VISIBLE
                messageTextView.setText(R.string.cannot_load_wadir)
                Toast.makeText(activity, getString(R.string.cannot_load_wadir), Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun executeOld() {
        Executors.newSingleThreadExecutor().execute {
            val mainHandler = Handler(Looper.getMainLooper())
            val statusFiles = Common.WB_STATUS_DIRECTORY.listFiles()
            imagesList.clear()

            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    if (file.name.contains(".nomedia")) continue

                    val status = Status(file, file.name, file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        imagesList.add(status)
                    }
                }

                mainHandler.post {
                    if (imagesList.isEmpty()) {
                        messageTextView.visibility = View.VISIBLE
                        messageTextView.setText(R.string.no_files_found)
                    } else {
                        messageTextView.visibility = View.GONE
                        messageTextView.text = ""
                    }

                    imageAdapter = PhotoAdapter(imagesList, container)
                    recyclerView.adapter = imageAdapter
                    imageAdapter.notifyItemRangeChanged(0, imagesList.size)
                    progressBar.visibility = View.GONE
                }
            } else {
                mainHandler.post {
                    progressBar.visibility = View.GONE
                    messageTextView.visibility = View.VISIBLE
                    messageTextView.setText(R.string.no_files_found)
                    Toast.makeText(activity, getString(R.string.no_files_found), Toast.LENGTH_SHORT).show()
                }
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun executeNew() {
        Executors.newSingleThreadExecutor().execute {
            val mainHandler = Handler(Looper.getMainLooper())
            val list = requireActivity().contentResolver.persistedUriPermissions
            if (list.isNotEmpty()) {
                val baseUri = list[0].uri
                val path = "/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses"
                val encodedPathString = Uri.encode(path)
                val fullUri = Uri.parse(baseUri.toString() + encodedPathString)
                Log.d("URI_DEBUG", "Base URI: $baseUri")
                Log.d("URI_DEBUG", "Status URI: $fullUri")

                val file = DocumentFile.fromTreeUri(requireActivity(), baseUri)
                Log.d("Full URI", fullUri.toString())

                // The rest of your code that works with the file

                imagesList.clear()

                if (file == null) {
                    mainHandler.post {
                        progressBar.visibility = View.GONE
                        messageTextView.visibility = View.VISIBLE
                        messageTextView.setText(R.string.no_files_found)
                        Toast.makeText(activity, getString(R.string.no_files_found), Toast.LENGTH_SHORT).show()
                        swipeRefreshLayout.isRefreshing = false
                    }
                    return@execute
                }

                val statusFiles = file.listFiles()

                if (statusFiles.isEmpty()) {
                    mainHandler.post {
                        progressBar.visibility = View.GONE
                        messageTextView.visibility = View.VISIBLE
                        messageTextView.setText(R.string.no_files_found)
                        Toast.makeText(activity, getString(R.string.no_files_found), Toast.LENGTH_SHORT).show()
                        swipeRefreshLayout.isRefreshing = false
                    }
                    return@execute
                }

                for (documentFile in statusFiles) {
                    if (documentFile.name?.contains(".nomedia") == true) continue

                    val status = Status(documentFile)
                    if (!status.isVideo) {
                        imagesList.add(status)
                    }
                }

                mainHandler.post {
                    if (imagesList.isEmpty()) {
                        messageTextView.visibility = View.VISIBLE
                        messageTextView.setText(R.string.no_files_found)
                    } else {
                        messageTextView.visibility = View.GONE
                        messageTextView.text = ""
                    }

                    imageAdapter = PhotoAdapter(imagesList, container)
                    recyclerView.adapter = imageAdapter
                    imageAdapter.notifyItemRangeChanged(0, imagesList.size)
                    progressBar.visibility = View.GONE
                }
            }

        }
    }
}
