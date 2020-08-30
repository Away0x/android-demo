package com.away0x.com.viewpager.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.com.viewpager.databinding.FragmentScaleBinding
import kotlin.random.Random

class ScaleFragment : Fragment() {

    private lateinit var binding: FragmentScaleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.imageViewScale.setOnClickListener {
            val factor = if (Random.nextBoolean()) 0.1f else -0.1f
            ObjectAnimator.ofFloat(it, "scaleX", it.scaleX + factor).start()
            ObjectAnimator.ofFloat(it, "scaleY", it.scaleY + factor).start()
        }
    }

}