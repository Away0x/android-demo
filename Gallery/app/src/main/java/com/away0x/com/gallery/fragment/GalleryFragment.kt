package com.away0x.com.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.away0x.com.gallery.R
import com.away0x.com.gallery.adapter.GalleryAdapter
import com.away0x.com.gallery.databinding.FragmentGalleryBinding
import com.away0x.com.gallery.viewModel.GalleryViewModel
import com.away0x.com.gallery.viewModel.GalleryViewModelFactory


class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val galleryViewModel: GalleryViewModel by viewModels { GalleryViewModelFactory(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        val galleryAdapter = GalleryAdapter()
        binding.recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        // 监听数据变化
        galleryViewModel.photoListLive.observe(viewLifecycleOwner, Observer {
            galleryAdapter.submitList(it)
            binding.swipeLayoutGallery.isRefreshing = false
        })

        // 没有数据的话，加载数据
        galleryViewModel.photoListLive.value ?: galleryViewModel.fetchData()

        binding.swipeLayoutGallery.setOnRefreshListener {
            galleryViewModel.fetchData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.swipeIndicator -> {
                binding.swipeLayoutGallery.isRefreshing = true
                // 加载数据，延迟 1s
                Handler().postDelayed(Runnable {
                    galleryViewModel.fetchData()
                }, 1000)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}