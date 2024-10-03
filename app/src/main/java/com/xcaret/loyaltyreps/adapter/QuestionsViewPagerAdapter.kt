package com.xcaret.loyaltyreps.adapter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.ResponseQuestionChoice
import com.xcaret.loyaltyreps.data.api.VideoQuizQuestion
import com.xcaret.loyaltyreps.data.api.XQuestionChoice
import com.xcaret.loyaltyreps.databinding.ItemPagerQuestionQuizBinding

class QuestionsViewPagerAdapter(private val fragments: List<Fragment>, fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        val fragment = fragments[position]
        return fragment
    }
}

class QuestionFragment(private val questionItem: VideoQuizQuestion,private val clickNext: (res : ResponseQuestionChoice)->Unit): Fragment() {
    private val question = questionItem
    private var response = ResponseQuestionChoice( question = questionItem)
    private var _binding: ItemPagerQuestionQuizBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemPagerQuestionQuizBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("QuestionFragment", "onViewCreated: $question")
        binding.txtQuestionTitle.text = questionItem.question
        binding.btnNextQuestion.setOnClickListener {
            clickNext(response)
        }
        printOptions()
    }
    private fun printOptions(){
        binding.quizQuestionOptionsContainer.removeAllViews()
        for (choice in question.choices){
            if(choice.is_correct){
                response.answer = choice
            }
           binding.quizQuestionOptionsContainer.addView(createOptionItem(choice))
        }
    }
    private fun createOptionItem(option: XQuestionChoice) : RadioButton {
        val mOption = RadioButton(activity)
        mOption.id = option.id
        mOption.text = option.option
        mOption.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        mOption.compoundDrawablePadding = 15
        mOption.isChecked = false
        mOption.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.btnNextQuestion.isEnabled = true
                response.selected = option
                response.is_correct = option.is_correct
            }
//            if (isChecked) {
//                pickedAnswer = option.is_correct!!
//                items_selected +=1
//                binding.quizError.visibility = View.GONE
//            } else {
//                if (items_selected > 0) { items_selected -=1 }
//                if (items_selected == 0) {
//                    binding.quizError.visibility = View.VISIBLE
//                }
//            }
        }
        mOption.setBackgroundColor(Color.TRANSPARENT)
        return mOption

    }
}