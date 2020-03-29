package com.away0x.calculation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.away0x.calculation.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    lateinit var binding: FragmentQuestionBinding
    lateinit var viewModel: CalcViewModel
    private val strbuilder = StringBuilder()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        ).get(CalcViewModel::class.java)
        viewModel.getCurrentScore().value = 0
        viewModel.generator()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = requireActivity()

        initView()

        return binding.root
    }

    fun initView() {
        binding.button0.setOnClickListener(numClickListener)
        binding.button1.setOnClickListener(numClickListener)
        binding.button2.setOnClickListener(numClickListener)
        binding.button3.setOnClickListener(numClickListener)
        binding.button4.setOnClickListener(numClickListener)
        binding.button5.setOnClickListener(numClickListener)
        binding.button6.setOnClickListener(numClickListener)
        binding.button7.setOnClickListener(numClickListener)
        binding.button8.setOnClickListener(numClickListener)
        binding.button9.setOnClickListener(numClickListener)

        binding.buttonClear.setOnClickListener {
            strbuilder.setLength(0)
            binding.tvAnswer.setText(R.string.input_indicator)
        }

        binding.buttonSubmit.setOnClickListener {
            if (strbuilder.isEmpty()) {
                Toast.makeText(requireContext(), R.string.answer_indicator_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (strbuilder.toString().toInt() == viewModel.getAnswer().value) {
                viewModel.answerCorrect()
                strbuilder.setLength(0)
                binding.tvAnswer.setText(R.string.answer_correct_message)
            } else {
                val navController = Navigation.findNavController(it)

                if (viewModel.winFlag) {
                    val action = QuestionFragmentDirections.actionQuestionFragmentToWinFragment()
                    navController.navigate(action)

                    viewModel.saveHighScore()
                    viewModel.winFlag = false
                } else {
                    val action = QuestionFragmentDirections.actionQuestionFragmentToLoseFragment()
                    navController.navigate(action)
                }
            }
        }
    }

    private val numClickListener = View.OnClickListener {
        val btn = it as Button
        strbuilder.append(btn.text)
        binding.tvAnswer.text = strbuilder.toString()
    }

}
