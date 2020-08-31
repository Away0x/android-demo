package com.away0x.com.drawerdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.com.drawerdemo.R
import kotlinx.android.synthetic.main.content_layout.*
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 设置 title
        requireActivity().collapsingToolBarLayout.title = getString(R.string.pager_fragment_title)
        requireActivity().toolbarIconImageView.setImageResource(R.drawable.ic_looks_three)

        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyListAdapter(true)
        viewPager2.adapter = adapter
        adapter.submitList(iconList())
    }

}