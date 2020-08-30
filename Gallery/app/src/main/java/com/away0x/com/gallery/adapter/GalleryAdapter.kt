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

class GalleryAdapter : ListAdapter<PhotoItem, MyViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GalleryCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MyViewHolder(binding)

        holder.itemView.setOnClickListener {
            val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFragment(
                currentList.toTypedArray(),
                holder.adapterPosition
            )
            holder.itemView.findNavController().navigate(action)
        }

        return holder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photoItem = getItem(position)

        with(holder.binding) {
            shimmerLayoutCell.apply {
                setShimmerColor(0x55FFFFFF)
                setShimmerAngle(0)
                startShimmerAnimation()
            }

            textViewUser.text = photoItem.photoUser
            textViewLikes.text = photoItem.photoLikes.toString()
            textViewFavorites.text = photoItem.photoFavorites.toString()

            // item 确定高度后，瀑布流布局就不会因为计算高度而产生重排
            imageView.layoutParams.height = photoItem.photoHeight
        }

        Glide.with(holder.binding.root)
            .load(photoItem.previewUrl)
            // .placeholder(R.drawable.ic_photo_gray_24dp)
            .placeholder(R.drawable.photo_placeholder)
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
    object DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem.photoId == newItem.photoId
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
    }
}

class MyViewHolder(val binding: GalleryCellBinding) : RecyclerView.ViewHolder(binding.root)
