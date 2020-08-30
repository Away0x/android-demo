package com.away0x.com.viewpager.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.com.viewpager.R
import com.away0x.com.viewpager.databinding.FragmentRotateBinding

class RotateFragment : Fragment() {

    private lateinit var binding: FragmentRotateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRotateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.imageViewRotate.setOnClickListener {
            ObjectAnimator.ofFloat(it, "rotation", it.rotation + 30f).start()
        }
    }

}