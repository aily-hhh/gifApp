package com.hhh.gifapp.ui.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hhh.gifapp.R
import com.hhh.gifapp.model.GifData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GifAdapter: RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    inner class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifItem: ImageView = itemView.findViewById(R.id.gifItem)
    }

    private val callback = object: DiffUtil.ItemCallback<GifData>() {
        override fun areItemsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return if (oldItem != newItem) {
                false
            } else {
                oldItem.images == newItem.images
            }
        }

    }

    private val differ = AsyncListDiffer(this, callback)

    fun setDiffer(list: List<GifData>) {
        this.differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        return GifViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.gif_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentGif = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(holder.itemView.context)
                .load(currentGif.images.original.url)
                .into(holder.gifItem)

            setOnClickListener {
                clickListener!!.onClickListener(differ.currentList[holder.adapterPosition].images.original.url)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var clickListener: GifClickListener? = null
    fun setClickListener(clickListener: GifClickListener) {
        this.clickListener = clickListener
    }
}

interface GifClickListener {
    fun onClickListener(urlCurrent: String)
}