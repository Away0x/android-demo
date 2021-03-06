package com.away0x.com.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.away0x.com.gallery.R
import com.away0x.com.gallery.adapter.GalleryAdapter
import com.away0x.com.gallery.databinding.FragmentGalleryBinding
import com.away0x.com.gallery.viewModel.DATA_STATUS_NETWORK_ERROR
import com.away0x.com.gallery.viewModel.GalleryViewModel
import com.away0x.com.gallery.viewModel.GalleryViewModelFactory
import kotlinx.android.synthetic.main.fragment_gallery.*


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

        val galleryAdapter = GalleryAdapter(galleryViewModel)
        binding.recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        // 监听数据变化
        galleryViewModel.photoListLive.observe(viewLifecycleOwner, Observer {
            if (galleryViewModel.needToScrollToTop) {
                recyclerView.scrollToPosition(0)
                galleryViewModel.needToScrollToTop = false
            }

            galleryAdapter.submitList(it)
            binding.swipeLayoutGallery.isRefreshing = false
        })
        galleryViewModel.dataStatusLive.observe(viewLifecycleOwner, Observer {
            galleryAdapter.footerViewStatus = it
            galleryAdapter.notifyItemChanged(galleryAdapter.itemCount - 1)
            if (it == DATA_STATUS_NETWORK_ERROR) swipeLayoutGallery.isRefreshing = false
        })

        // 没有数据的话，加载数据
        galleryViewModel.photoListLive.value ?: galleryViewModel.fetchData()

        binding.swipeLayoutGallery.setOnRefreshListener {
            galleryViewModel.resetQuery()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) return

                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val lastPositions = IntArray(layoutManager.spanCount)
                layoutManager.findLastVisibleItemPositions(lastPositions)

                // 页脚出现了，滑动到底部了
                if (lastPositions[0] == galleryAdapter.itemCount - 1) {
                    galleryViewModel.fetchData()
                }
            }
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
                Handler().postDelayed(Runnable {
                    galleryViewModel.resetQuery()
                }, 1000)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}