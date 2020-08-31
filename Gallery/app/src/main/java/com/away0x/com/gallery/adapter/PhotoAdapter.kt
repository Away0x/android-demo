package com.away0x.com.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.gallery.R
import com.away0x.com.gallery.databinding.PhotoViewBinding
import com.away0x.com.gallery.model.PhotoItem
import com.bumptech.glide.Glide

class PhotoAdapter : ListAdapter<PhotoItem, PhotoViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem.photoId == newItem.photoId
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindWith(getItem(position))
    }

}

class PhotoViewHolder(val binding: PhotoViewBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup): PhotoViewHolder {
            val binding = PhotoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PhotoViewHolder(binding)
        }
    }

    fun bindWith(photoItem: PhotoItem) {
        Glide.with(binding.root)
            .load(photoItem.previewUrl)
            .placeholder(R.drawable.photo_placeholder)
            .into(binding.pagerPhoto)
    }
}