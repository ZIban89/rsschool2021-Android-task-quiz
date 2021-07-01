package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = _binding!!
    private lateinit var frAction: ResultFragmentAction

    override fun onAttach(context: Context) {
        super.onAttach(context)
        frAction = context as ResultFragmentAction
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            exitProcess(0)
        }

        binding.restart.setOnClickListener {
            frAction.resetQuiz()
        }

        binding.share.setOnClickListener {

            val text = frAction.getText()
            val intent = Intent(Intent.ACTION_SEND)
            with(intent) {
                type = "plain/text"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.textView.text = String.format(getString(R.string.your_result), frAction.getResult())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun getInstance(questionNumber: Int): ResultFragment {
            val fragment = ResultFragment()
            fragment.arguments = bundleOf(NUMBER_OF_FRAGMENT to questionNumber)
            return fragment
        }

        private const val NUMBER_OF_FRAGMENT = "NUMBER"
    }

    interface ResultFragmentAction {
        fun getResult(): Int
        fun resetQuiz()
        fun getText(): String
    }
}