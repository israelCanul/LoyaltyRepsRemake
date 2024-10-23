package com.xcaret.loyaltyreps.view.parks.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.databinding.FragmentParksBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.parks.vm.ParksViewModel


class Parks() : BaseFragmentDataBinding<FragmentParksBinding>(){
    lateinit var _viewModel: ParksViewModel
    override val tagForBar: String
        get() = "ParksFragment"
    override fun setHeaderFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[ParksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentParksBinding.inflate(inflater))
        setHeaderFragment()
        observer()
        return binding.root
    }

    private fun observer() {
        _viewModel.listParksTraining.observe(viewLifecycleOwner){
            Log.i(tagForBar, "observer: $it")
            it?.let{
                loadingDialog.dismiss()
            }?:run{
                loadingDialog.show(parentFragmentManager,tagForBar)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        _viewModel.fetchTrainingData()
    }


}