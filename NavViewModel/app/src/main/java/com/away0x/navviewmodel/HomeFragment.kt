package com.away0x.navviewmodel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.away0x.navviewmodel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var myViewModel: MyViewModel
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        myViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.data = myViewModel
        binding.lifecycleOwner = requireActivity()

        initView()

        return binding.root
    }

    fun initView() {
        binding.button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment)
        }

        binding.seekBar.progress = myViewModel.getNumber().value ?: 0
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                myViewModel.getNumber().value = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

}
