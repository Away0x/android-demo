package com.away0x.com.viewpager.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.away0x.com.viewpager.R
import com.away0x.com.viewpager.databinding.FragmentTranslateBinding
import kotlin.random.Random

class TranslateFragment : Fragment() {

    private lateinit var binding: FragmentTranslateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTranslateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.imageViewTranslate.setOnClickListener {
            ObjectAnimator.ofFloat(it, "translationX", it.translationX + Random.nextInt(-100, 100)).start()
        }
    }

}