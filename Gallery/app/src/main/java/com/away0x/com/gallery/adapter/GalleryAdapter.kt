package com.away0x.com.gallery.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.gallery.R
import com.away0x.com.gallery.databinding.GalleryCellBinding
import com.away0x.com.gallery.fragment.GalleryFragmentDirections
import com.away0x.com.gallery.model.PhotoItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GalleryAdapter : ListAdapter<PhotoItem, MyViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GalleryCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MyViewHolder(binding)

        holder.itemView.setOnClickListener {
            // 1. 普通路由传参
            // val bundle = Bundle().apply {
            //     putParcelable("PHOTO", getItem(holder.adapterPosition))
            // }
            // holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_photoFragment, bundle)

            // 2. safe args 路由传参
            val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFragment(getItem(holder.adapterPosition))
            holder.itemView.findNavController().navigate(action)
        }

        return holder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // placeholder image
        holder.binding.shimmerLayoutCell.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }

        Glide.with(holder.binding.root)
            .load(getItem(position).previewUrl)
            .placeholder(R.drawable.ic_photo_gray_24dp)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false.also {
                        holder.binding.shimmerLayoutCell?.stopShimmerAnimation()
                    }
                }
            })
            .into(holder.binding.imageView)
    }

    // 列表数据的差异化比对是在后台异步执行的
    object DIFFCALLBACK : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }
    }
}

class MyViewHolder(val binding: GalleryCellBinding) : RecyclerView.ViewHolder(binding.root)
