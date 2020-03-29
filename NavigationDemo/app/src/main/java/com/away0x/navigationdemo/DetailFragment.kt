package com.away0x.navigationdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 普通接收参数方式
        // arguments?.getString("name").let { textView2.text = it }

        // safe args
        arguments?.let {
            textView2.text = DetailFragmentArgs.fromBundle(it).name
        }


        initViewEvent()
    }

    fun initViewEvent() {
        button2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }

}
