package com.away0x.com.gallery.fragment

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.away0x.com.gallery.R
import com.away0x.com.gallery.adapter.PhotoAdapter
import com.away0x.com.gallery.adapter.PhotoViewHolder
import com.away0x.com.gallery.databinding.FragmentPhotoBinding
import com.away0x.com.gallery.viewModel.GalleryViewModel
import com.away0x.com.gallery.viewModel.GalleryViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val REQUEST_WRITE_EXTERNAL_STORAGE = 1

class PhotoFragment : Fragment() {

    private val galleryViewModel: GalleryViewModel by activityViewModels { GalleryViewModelFactory(requireActivity().application) }

    private val args by navArgs<PhotoFragmentArgs>()
    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        savePhoto()
                    }
                } else {
                    Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        initViewPager()

        // 点击下载按钮
        binding.saveButton.setOnClickListener {
            // 小于 29 版本，不需动态申请权限
            val checkStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT < 29 && checkStatus != PackageManager.PERMISSION_GRANTED) {
                // 请求权限
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE
                )
            } else {
                // 已有权限了
                // viewLifecycleOwner.lifecycleScope: 父线程结束，该线程也会被终止
                viewLifecycleOwner.lifecycleScope.launch {
                    savePhoto()
                }
            }
        }
    }

    /**
     * 初始化 viewPager2
     */
    private fun initViewPager() {
        val currentPosition = args.currentPosition

        val adapter = PhotoAdapter()
        binding.viewPager2.adapter = adapter
        // 垂直滚动
        // binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL

        galleryViewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.viewPager2.setCurrentItem(currentPosition, false)
        })


        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.photoTag.text = getString(R.string.photo_tag, position + 1, galleryViewModel.pagedListLiveData.value?.size)
            }
        })
    }

    /**
     * 保存图片到相册 (放在 IO 线程中执行)
     */
    private suspend fun savePhoto() {
        withContext(Dispatchers.IO) {
            // viewPager 里面封装了个 RecyclerView，所以这里取出 RecyclerView，然后根据其得到 viewHolder
            val recyclerView = binding.viewPager2[0] as RecyclerView
            // 取出当前 viewHolder 中的图片
            val holder = recyclerView.findViewHolderForAdapterPosition(binding.viewPager2.currentItem) as PhotoViewHolder
            val bitmap = holder.binding.pagerPhoto.drawable.toBitmap()

            // 获取保存路径
            val saveUri = requireContext().contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())

            if (saveUri == null) {
                Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()
                return@withContext
            }

            // 保存
            requireContext().contentResolver.openOutputStream(saveUri).use {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)) {
                    MainScope().launch {Toast.makeText(requireContext(), "存储成功", Toast.LENGTH_SHORT).show() }
                } else {
                    MainScope().launch { Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }

}