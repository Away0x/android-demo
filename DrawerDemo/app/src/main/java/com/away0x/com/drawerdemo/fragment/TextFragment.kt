package com.away0x.com.drawerdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.com.drawerdemo.R
import kotlinx.android.synthetic.main.content_layout.*

class TextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 设置 title
        requireActivity().collapsingToolBarLayout.title = getString(R.string.text_fragment_title)
        requireActivity().toolbarIconImageView.setImageResource(R.drawable.ic_looks_one)

        return inflater.inflate(R.layout.fragment_text, container, false)
    }
}