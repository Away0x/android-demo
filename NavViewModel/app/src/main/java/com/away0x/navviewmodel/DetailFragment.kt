package com.away0x.navviewmodel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.away0x.navviewmodel.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var myViewModel: MyViewModel
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        myViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.data = myViewModel
        binding.lifecycleOwner = requireActivity()

        initView()

        return binding.root
    }

    fun initView() {
        binding.button4.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }

}
