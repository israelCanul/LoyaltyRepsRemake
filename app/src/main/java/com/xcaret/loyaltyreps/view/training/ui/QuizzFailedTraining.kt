package com.xcaret.loyaltyreps.view.training.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.databinding.FragmentQuizFailedTrainingBinding
import com.xcaret.loyaltyreps.databinding.FragmentVideoQuizzTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.training.vm.VideoQuizzViewModel

class QuizzFailedTraining: BaseFragmentDataBinding<FragmentQuizFailedTrainingBinding>() {
    override val tagForBar: String
        get() = "QuizzFailedTraining"

    override fun setHeaderFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentQuizFailedTrainingBinding.inflate(inflater))

        setHeaderFragment()
        observer()
        listeners()
        return binding.root
    }

    private fun listeners() {
        onBackPressed(){
            navigate(R.id.training)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun observer() {
        _parentActivity?._viewModel?.currentUser?.observe(viewLifecycleOwner) {
            val puntos = it?.puntosParaArticulos
            binding.pointsOwned.text = puntos.toString()
        }
    }
}