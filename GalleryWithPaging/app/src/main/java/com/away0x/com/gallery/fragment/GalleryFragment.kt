package com.away0x.com.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        galleryViewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer {
            galleryAdapter.submitList(it)
            binding.swipeLayoutGallery.isRefreshing = false
        })

        binding.swipeLayoutGallery.setOnRefreshListener {
            galleryViewModel.resetQuery()
        }
    }

    /** 创建一个顶部导航栏右侧的 menu */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    /** menu 的选择事件 */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.swipeIndicator -> {
                binding.swipeLayoutGallery.isRefreshing = true
                // 加载数据，延迟 1s
                Handler().postDelayed(Runnable { galleryViewModel.resetQuery() }, 1000)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}