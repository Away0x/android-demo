package com.away0x.calculation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.away0x.calculation.databinding.FragmentWinBinding

class WinFragment : Fragment() {

    lateinit var binding: FragmentWinBinding
    lateinit var viewModel: CalcViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        ).get(CalcViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_win, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = requireActivity()

        initView()

        return binding.root
    }

    fun initView() {
        binding.backBtn.setOnClickListener {
            val action = WinFragmentDirections.actionWinFragmentToTitleFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

}
