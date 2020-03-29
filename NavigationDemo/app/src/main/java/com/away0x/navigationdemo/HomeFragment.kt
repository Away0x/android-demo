package com.away0x.navigationdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button.setOnClickListener {
            val text = editText.text.toString()
            if (text == "") {
                Toast.makeText(activity, "请输入名字", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // 普通传参方式
            // val bundle = Bundle().apply { putString("name", text) }
            // Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment, bundle)

            // safe args
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(text)
            Navigation.findNavController(it).navigate(action)
        }
    }

}
