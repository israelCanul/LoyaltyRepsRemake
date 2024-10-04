package com.xcaret.loyaltyreps.view.training.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.databinding.FragmentQuizFailedTrainingBinding
import com.xcaret.loyaltyreps.databinding.FragmentQuizSuccessTrainingBinding
import com.xcaret.loyaltyreps.databinding.FragmentVideoQuizzTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.training.vm.VideoQuizzViewModel

class QuizzSuccessTraining: BaseFragmentDataBinding<FragmentQuizSuccessTrainingBinding>() {
    override val tagForBar: String
        get() = "QuizzFailedTraining"
    lateinit var nameQuiz: String
    lateinit var pointsQuiz: String

    override fun setHeaderFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentQuizSuccessTrainingBinding.inflate(inflater))

        nameQuiz = arguments?.getString("name_quizz")?: "Sample"
        pointsQuiz = arguments?.getString("points_quizz")?:"100"

        setHeaderFragment()
        observer()
        listeners()

        binding.txtQuizName.text = nameQuiz
        binding.txtPointsEarned.text = "$pointsQuiz  pts"


        return binding.root
    }

    private fun listeners() {
        onBackPressed(){
            navigate(R.id.training)
        }
        binding.btnContinueTraining.setOnClickListener {
            navigate(R.id.training)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun observer() {

    }
}