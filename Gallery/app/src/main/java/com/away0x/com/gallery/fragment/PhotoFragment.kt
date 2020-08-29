package com.away0x.com.gallery.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.away0x.com.gallery.R
import com.away0x.com.gallery.databinding.FragmentPhotoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class PhotoFragment : Fragment() {

    private val args by navArgs<PhotoFragmentArgs>()
    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 1. 普通传参方式接收参数
        // 发出方
        // val bundle = Bundle().apply { putParcelable("PHOTO", getItem(holder.adapterPosition)) }
        // xx.findNavController().navigate(R.id.action_galleryFragment_to_photoFragment, bundle)
        // 接收方
        // val url = arguments?.getParcelable<PhotoItem>("PHOTO")?.fullUrl

        // 2. safe args 接收参数
        val url = args.photo.fullUrl

        binding.shimmerLayoutPhoto.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }

        Glide.with(requireContext())
            .load(url)
            .placeholder(R.drawable.ic_photo_gray_24dp)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false.also { binding.shimmerLayoutPhoto?.stopShimmerAnimation() }
                }
            })
            .into(binding.photoView)
    }
}