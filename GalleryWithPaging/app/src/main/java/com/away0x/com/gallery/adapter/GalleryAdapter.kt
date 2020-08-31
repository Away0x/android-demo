package com.away0x.com.gallery.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.away0x.com.gallery.R
import com.away0x.com.gallery.databinding.GalleryCellBinding
import com.away0x.com.gallery.databinding.GalleryFooterBinding
import com.away0x.com.gallery.fragment.GalleryFragmentDirections
import com.away0x.com.gallery.model.PhotoItem
import com.away0x.com.gallery.paging.NetworkStatus
import com.away0x.com.gallery.viewModel.GalleryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

const val NORMAL_CELL_TYPE = 0
const val FOOTER_CELL_TYPE = 1

class GalleryAdapter(private val galleryViewModel: GalleryViewModel) : PagedListAdapter<PhotoItem, RecyclerView.ViewHolder>(DiffCallback) {

    private var networkStatus: NetworkStatus? = null
    private var hasFooter = false
    private val footerItemPosition get() = itemCount - 1

    // 如有需要重试的请求，则单适配器初始化时自动重试
    init {
        galleryViewModel.retry()
    }

    fun updateNetworkStatus(networkStatus: NetworkStatus?) {
        this.networkStatus = networkStatus
        if (networkStatus == NetworkStatus.INITIAL_LOADING) hideFooter() else showFooter()
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + (if (hasFooter) 1 else 0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasFooter && position == footerItemPosition) FOOTER_CELL_TYPE else NORMAL_CELL_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FOOTER_CELL_TYPE -> FooterViewHolder.newInstance(parent).also {
                    it.binding.root.setOnClickListener {
                        galleryViewModel.retry()
                    }
            }
            else -> NormalCellViewHolder.newInstance(parent).also { holder ->
                holder.binding.root.setOnClickListener {
                    val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFragment(holder.adapterPosition)
                    holder.binding.root.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            FOOTER_CELL_TYPE -> {
                with(holder as FooterViewHolder) { bind(networkStatus) }
            }
            else -> {
                val photoItem = getItem(position) ?: return
                with(holder as NormalCellViewHolder) { bind(photoItem) }
            }
        }
    }

    // 列表数据的差异化比对是在后台异步执行的
    object DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem.photoId == newItem.photoId
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
    }

    private fun hideFooter() {
        if (hasFooter) notifyItemRemoved(footerItemPosition)
        hasFooter = false
    }

    private fun showFooter() {
        if (hasFooter) {
            notifyItemChanged(footerItemPosition)
        } else {
            hasFooter = true
            notifyItemInserted(footerItemPosition)
        }
    }
}

class NormalCellViewHolder(val binding: GalleryCellBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup): NormalCellViewHolder {
            val binding = GalleryCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NormalCellViewHolder(binding)
        }
    }

    fun bind(photoItem: PhotoItem) {
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

    fun bind(networkStatus: NetworkStatus?) {
        with(binding) {
            when (networkStatus) {
                NetworkStatus.FAILED -> {
                    textView.text = "点击重试"
                    progressBar.visibility = View.GONE
                    root.isClickable = true
                }
                NetworkStatus.COMPLETED -> {
                    textView.text = "加载完毕"
                    progressBar.visibility = View.GONE
                    root.isClickable = false
                }
                else -> {
                    textView.text = "正在加载"
                    progressBar.visibility = View.VISIBLE
                    root.isClickable = false
                }
            }
        }
    }
}