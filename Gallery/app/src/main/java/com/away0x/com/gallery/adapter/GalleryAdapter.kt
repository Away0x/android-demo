package com.away0x.com.gallery.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.away0x.com.gallery.R
import com.away0x.com.gallery.databinding.GalleryCellBinding
import com.away0x.com.gallery.databinding.GalleryFooterBinding
import com.away0x.com.gallery.fragment.GalleryFragmentDirections
import com.away0x.com.gallery.model.PhotoItem
import com.away0x.com.gallery.viewModel.DATA_STATUS_CAN_LOAD_MORE
import com.away0x.com.gallery.viewModel.DATA_STATUS_NETWORK_ERROR
import com.away0x.com.gallery.viewModel.DATA_STATUS_NO_MORE
import com.away0x.com.gallery.viewModel.GalleryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

const val NORMAL_CELL_TYPE = 0
const val FOOTER_CELL_TYPE = 1

class GalleryAdapter(private val galleryViewModel: GalleryViewModel) : ListAdapter<PhotoItem, RecyclerView.ViewHolder>(DiffCallback) {

    // footer 状态
    var footerViewStatus = DATA_STATUS_CAN_LOAD_MORE

    override fun getItemCount() = super.getItemCount() + 1 // + 1 是为了显示 footer

    // 最后一行返回 footer
    override fun getItemViewType(position: Int) = if (isFooter(position)) FOOTER_CELL_TYPE else NORMAL_CELL_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FOOTER_CELL_TYPE -> FooterViewHolder.newInstance(parent).also {
                it.binding.progressBar.visibility = View.VISIBLE
                it.binding.textView.text = "正在加载"
                galleryViewModel.fetchData()
            }
            else -> NormalCellViewHolder.newInstance(parent).also { holder ->
                holder.binding.root.setOnClickListener {
                    val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFragment(
                        currentList.toTypedArray(),
                        holder.adapterPosition
                    )
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            FOOTER_CELL_TYPE -> with(holder as FooterViewHolder) {
                bindWith(footerViewStatus)
            }
            else -> with(holder as NormalCellViewHolder) {
                val photoItem = getItem(position) ?: return
                bindWith(photoItem)
            }
        }
    }

    // 列表数据的差异化比对是在后台异步执行的
    object DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem.photoId == newItem.photoId
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
    }

    private fun isFooter(position: Int) = position == itemCount - 1
}

class NormalCellViewHolder(val binding: GalleryCellBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup): NormalCellViewHolder {
            val binding = GalleryCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NormalCellViewHolder(binding)
        }
    }

    fun bindWith(photoItem: PhotoItem) {
        with(binding) {
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

        Glide.with(binding.root)
            .load(photoItem.previewUrl)
            // .placeholder(R.drawable.ic_photo_gray_24dp)
            .placeholder(R.drawable.photo_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also {
                        binding.shimmerLayoutCell?.stopShimmerAnimation()
                    }
                }
            })
            .into(binding.imageView)
    }
}

class FooterViewHolder(val binding: GalleryFooterBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup): FooterViewHolder {
            val binding = GalleryFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            (binding.root.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
            return FooterViewHolder(binding)
        }
    }

    fun bindWith(footerViewStatus: Int) {
        with(binding) {
            when (footerViewStatus) {
                DATA_STATUS_CAN_LOAD_MORE -> {
                    progressBar.visibility = View.VISIBLE
                    textView.text = "正在加载"
                    root.isClickable = false
                }
                DATA_STATUS_NO_MORE -> {
                    progressBar.visibility = View.GONE
                    textView.text = "全部加载完毕"
                    root.isClickable = false
                }
                DATA_STATUS_NETWORK_ERROR -> {
                    progressBar.visibility = View.GONE
                    textView.text = "网络故障，点击重试"
                    root.isClickable = true
                }
            }
        }
    }
}