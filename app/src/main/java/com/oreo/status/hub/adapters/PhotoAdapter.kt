package com.oreo.status.hub.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.RelativeLayout
import com.oreo.status.hub.R
import com.oreo.status.hub.models.Status
import com.oreo.status.hub.utils.Common

class PhotoAdapter(
    private val imagesList: List<Status>,
    private val container: RelativeLayout
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val play: ImageView = view.findViewById(R.id.imagePlay)
        //val share: ImageView = view.findViewById(R.id.share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status = imagesList[position]
        val layoutParams = holder.itemView.layoutParams

        // Set layout parameters to wrap content
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        holder.itemView.layoutParams = layoutParams
        Glide.with(context!!).load(status.documentFile.uri).into(holder.imageView)
        if (status.isApi30) {
            // holder.save.visibility = View.GONE
            //Glide.with(context!!).load(status.documentFile.uri).into(holder.imageView)
        } else {
            // holder.save.visibility = View.VISIBLE
            //Glide.with(context!!).load(status.file).into(holder.imageView)
        }

        // Uncomment and customize these event listeners as needed
        // holder.save.setOnClickListener {
        //     Common.copyFile(status, context!!, container)
        // }

        // holder.share.setOnClickListener {
        //     val shareIntent = Intent(Intent.ACTION_SEND)
        //     shareIntent.type = "image/jpg"
        //     if (status.isApi30) {
        //         shareIntent.putExtra(Intent.EXTRA_STREAM, status.documentFile.uri)
        //     } else {
        //         shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://${status.file.absolutePath}"))
        //     }
        //     context!!.startActivity(Intent.createChooser(shareIntent, "Share image"))
        // }

        // holder.imageView.setOnClickListener {
        //     val alertD = AlertDialog.Builder(context)
        //     val inflater = LayoutInflater.from(context)
        //     val view = inflater.inflate(R.layout.view_image_full_screen, null)
        //     alertD.setView(view)
        //     val imageView = view.findViewById<ImageView>(R.id.img)
        //     if (status.isApi30) {
        //         Glide.with(context!!).load(status.documentFile.uri).into(imageView)
        //     } else {
        //         Glide.with(context!!).load(status.file).into(imageView)
        //     }
        //     val alert = alertD.create()
        //     alert.window!!.attributes.windowAnimations = R.style.SlidingDialogAnimation
        //     alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //     alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //     alert.show()
        // }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}

