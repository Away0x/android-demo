package com.away0x.com.drawerdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.away0x.com.drawerdemo.R
import kotlinx.android.synthetic.main.content_layout.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 设置 title
        requireActivity().collapsingToolBarLayout.title = getString(R.string.liss_fragment_title)
        requireActivity().toolbarIconImageView.setImageResource(R.drawable.ic_looks_two)

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = MyListAdapter()
        recyclerView.adapter =adapter
        adapter.submitList(iconList())
    }

}
