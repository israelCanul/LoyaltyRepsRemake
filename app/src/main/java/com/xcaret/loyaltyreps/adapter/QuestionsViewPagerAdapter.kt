package com.xcaret.loyaltyreps.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.ResponseQuestionChoice
import com.xcaret.loyaltyreps.data.api.VideoQuizQuestion
import com.xcaret.loyaltyreps.databinding.ItemPagerQuestionQuizBinding

class QuestionsViewPagerAdapter(private val fragments: List<Fragment>, fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 100

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        val fragment = fragments[position]
        return fragment
    }
}

class QuestionFragment(private val questionItem: VideoQuizQuestion,private val clickNext: (res : ResponseQuestionChoice)->Unit): Fragment() {
    private val question = questionItem
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
            clickNext(ResponseQuestionChoice( question = questionItem))
        }
    }
}