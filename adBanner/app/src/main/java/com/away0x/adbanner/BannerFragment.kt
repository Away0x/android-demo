package com.away0x.adbanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class BannerFragment(
    val imgID: Int
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_banner, container, false)
        val img = inflate.findViewById<ImageView>(R.id.mBannerIV)
        val text = inflate.findViewById<TextView>(R.id.mBannerTV)

        img.setImageResource(imgID)
        text.text = imgID.toString()
        return inflate
    }

}
