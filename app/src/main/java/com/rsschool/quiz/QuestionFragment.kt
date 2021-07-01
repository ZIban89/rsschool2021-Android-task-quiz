package com.rsschool.quiz


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.Questions.questions
import com.rsschool.quiz.databinding.FragmentQuizBinding


class QuestionFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var frAction: QuestionFragmentAction


    override fun onAttach(context: Context) {
        super.onAttach(context)
        frAction = context as QuestionFragmentAction
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFields(arguments?.getInt(NUMBER_OF_FRAGMENT)!!)

    }


    private fun initFields(numb: Int) {
        with(binding.nextButton) {
            visibility = View.GONE
            setOnClickListener {
                frAction.changeFragment(numb + 1)
            }
            if (numb == 4) text = getString(R.string.submit_btn)
        }

        if (numb == 0) {
            binding.previousButton.visibility = View.GONE
            binding.toolbar.navigationIcon = null
        }

        val quest: Pair<String, List<String>> = questions[numb]!!

        binding.title.text = String.format(getString(R.string.question_numb), numb + 1)

        binding.question.text = quest.first

        binding.optionOne.text = quest.second[0]
        binding.optionTwo.text = quest.second[1]
        binding.optionThree.text = quest.second[2]
        binding.optionFour.text = quest.second[3]
        binding.optionFive.text = quest.second[4]


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.nextButton.visibility = View.VISIBLE
            saveAnswer(numb, checkedId)
        }


        binding.previousButton.setOnClickListener { frAction.changeFragment(numb - 1) }
        binding.toolbar.setNavigationOnClickListener { frAction.changeFragment(numb - 1) }
    }


    private fun saveAnswer(numb: Int, checkedId: Int) {
        val answer = when (checkedId) {
            R.id.option_one -> 0
            R.id.option_two -> 1
            R.id.option_three -> 2
            R.id.option_four -> 3
            else -> 4
        }
        frAction.saveAnswer(numb, answer)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun getInstance(questionNumber: Int): QuestionFragment {
            val fragment = QuestionFragment()
            fragment.arguments = bundleOf(NUMBER_OF_FRAGMENT to questionNumber)
            return fragment
        }

        private const val NUMBER_OF_FRAGMENT = "NUMBER"
    }


    interface QuestionFragmentAction {
        fun changeFragment(nextFragment: Int)
        fun saveAnswer(question: Int, answer: Int)
    }
}