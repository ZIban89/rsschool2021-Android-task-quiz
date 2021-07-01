package com.rsschool.quiz

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.data.Questions.questions
import com.rsschool.quiz.data.RightAnswers.rightAnswers
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), QuestionFragment.QuestionFragmentAction, ResultFragment.ResultFragmentAction {
    private lateinit var viewPager2:ViewPager2
    private val answers = IntArray(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2 = findViewById(R.id.viewPager2)
        viewPager2.adapter = ViewPagerFragmentAdapter(this)
        viewPager2.isUserInputEnabled = false
    }



    override fun changeFragment(nextFragment: Int){
        viewPager2.currentItem = nextFragment
        changeTheme(nextFragment)
    }

    override fun saveAnswer(question: Int, answer: Int) {
        answers[question] = answer
    }

    override fun getResult(): Int {
        var result = 0
        for(i in 0..4)
            if(rightAnswers[i] == answers[i])
                result++
        return result

    }

    override fun resetQuiz() {
        viewPager2.adapter = ViewPagerFragmentAdapter(this)
        changeTheme(viewPager2.currentItem)
    }

    override fun getText(): String {
        val sb = StringBuilder(String.format(getString(R.string.your_result), getResult()))
        for(i in 0..4){
            sb.append("\n\n${i+1}) ${questions[i]?.first}")
            sb.append("\n${String.format(getString(R.string.answer),
                questions[i]?.second?.get(answers[i]), answers[i] == rightAnswers[i])}")
        }
        return sb.toString()
    }



    private fun getThemeByNumber(number: Int?): Int{
        return when (number) {
            0 -> R.style.Theme_Quiz_First
            1 -> R.style.Theme_Quiz_Second
            2 -> R.style.Theme_Quiz_Third
            3 -> R.style.Theme_Quiz_Fourth
            4 -> R.style.Theme_Quiz_Fifth
            else -> R.style.Theme_Quiz_Result
        }
    }

    private fun changeTheme(number: Int){
        setTheme(getThemeByNumber(number))
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true)
        window.statusBarColor = typedValue.data
    }
}