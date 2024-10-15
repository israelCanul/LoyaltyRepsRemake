package com.xcaret.loyaltyreps.view.training.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.xcaret.loyaltyreps.data.api.TrainingDetail
import com.xcaret.loyaltyreps.databinding.FragmentTrainingExtrasParkDetailBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel

class TrainingExtrasParkDetailScreen() : BaseFragmentDataBinding<FragmentTrainingExtrasParkDetailBinding>(){
    private val viewModel: MainViewModel by activityViewModels()
    override val tagForBar: String
        get() = "TrainingExtrasParkDetailScreen"

    override fun setHeaderFragment() {}
    var mcontent: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentTrainingExtrasParkDetailBinding.inflate(inflater))
        setHeaderFragment()

        var trainingName = arguments?.getString("xtraining_name")
        var trainingDetail = arguments?.getString("xtraining_detail")
        var trainingId = arguments?.getInt("xtraining_id")


//        Log.i(tagForBar, "onCreateView: $trainingDetail")

        observers()
        return binding.root
    }
    fun setUpUi(xTraining: TrainingDetail){
        Log.i(tagForBar, "onCreateView: $xTraining")
        mcontent = "<!DOCTYPE html>"
        mcontent += "<head></head>"
        mcontent += "<body style=\"background-color:#f6eff6; font-size:13px;\"><div align=\"justify\">"
        mcontent += xTraining.description
        mcontent += "</div></body></html>"

        binding.txtTitle.text = xTraining.name
        binding.wvParkDescription.setBackgroundColor(Color.TRANSPARENT)
        binding.wvParkDescription.loadDataWithBaseURL(null, mcontent!!, "text/html", "UTF-8", null)

        binding.btnBack.setOnClickListener{
            popBackStack()
        }
    }

    private fun observers() {
        viewModel.trainingExtrasParkDetail.observe(viewLifecycleOwner){
            it?.let {
                setUpUi(it)
            }
        }
    }

}