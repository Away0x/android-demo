package com.away0x.wordslog.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.away0x.wordslog.Injection
import com.away0x.wordslog.data.room.Word

import com.away0x.wordslog.databinding.FragmentAddBinding
import com.away0x.wordslog.viewmodels.WordViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: WordViewModel by viewModels { Injection.provideWordViewModelFactory(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.buttonSubmit.isEnabled = false
        binding.editTextEnglish.requestFocus()

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextEnglish, 0)

        editTextEnglish.addTextChangedListener(textWatcher)
        editTextChinese.addTextChangedListener(textWatcher)

        buttonSubmit.setOnClickListener {
            val english = binding.editTextEnglish.text.toString().trim()
            val chinese = binding.editTextChinese.text.toString().trim()
            val word = Word(word = english, chineseMeaning = chinese)
            viewModel.insertWords(word)

            val navController = Navigation.findNavController(it)
            navController.navigateUp()

            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val english = binding.editTextEnglish.text.trim()
            val chinese = binding.editTextChinese.text.trim()
            binding.buttonSubmit.isEnabled = english.isNotEmpty() && chinese.isNotEmpty()
        }
    }

}
