package com.xcaret.loyaltyreps.view.training.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.databinding.FragmentTrainingBinding
import com.xcaret.loyaltyreps.databinding.FragmentVideoQuizzTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.training.vm.TrainingViewModel
import com.xcaret.loyaltyreps.view.training.vm.VideoQuizzViewModel

class VideoQuizzTraining:  BaseFragmentDataBinding<FragmentVideoQuizzTrainingBinding>(){
    override val tagForBar: String
        get() = "VideoQuizzFragment"
    lateinit var _viewModel: VideoQuizzViewModel

    override fun setHeaderFragment()  {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[VideoQuizzViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentVideoQuizzTrainingBinding.inflate(inflater))
        setHeaderFragment()
        observers()
        return binding.root
    }

    private fun observers() {
        _viewModel.videoQuizTraining.observe(viewLifecycleOwner){
            binding.txtQuestion.text = it?.questions?.get(0)?.question
        }
    }

}