package com.away0x.com.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.away0x.com.gallery.R
import com.away0x.com.gallery.adapter.GalleryAdapter
import com.away0x.com.gallery.databinding.FragmentGalleryBinding
import com.away0x.com.gallery.paging.NetworkStatus
import com.away0x.com.gallery.viewModel.GalleryViewModel
import com.away0x.com.gallery.viewModel.GalleryViewModelFactory


class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    // activityViewModels: viewModel 生命周期范围在 activity 内
    private val galleryViewModel: GalleryViewModel by activityViewModels { GalleryViewModelFactory(requireActivity().application) }

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

        val galleryAdapter = GalleryAdapter(galleryViewModel)
        binding.recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        galleryViewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer {
            if (galleryViewModel.needToScrollToTop) {
                binding.recyclerView.scrollToPosition(0)
                galleryViewModel.needToScrollToTop = false
            }

            galleryAdapter.submitList(it)
            binding.swipeLayoutGallery.isRefreshing = false
        })

        binding.swipeLayoutGallery.setOnRefreshListener {
            galleryViewModel.resetQuery()
        }

        galleryViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            Log.d("GalleryFragment", "networkStatus change: $it")
            galleryAdapter.updateNetworkStatus(it)
            binding.swipeLayoutGallery.isRefreshing = it == NetworkStatus.INITIAL_LOADING
        })
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
            R.id.menuRetry -> {
                galleryViewModel.retry()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}