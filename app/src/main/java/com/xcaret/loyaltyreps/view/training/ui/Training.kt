package com.xcaret.loyaltyreps.view.training.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.databinding.FragmentTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.training.vm.TrainingViewModel
import com.xcaret.loyaltyv2.adapter.SlideTrainingParksAdapter
import com.xcaret.loyaltyv2.adapter.SlideTrainingVideoAdapter

class Training(): BaseFragmentDataBinding<FragmentTrainingBinding>(), ParksTrainingListeners{
    override val tagForBar: String
        get() = "TrainingFragment"
    lateinit var _viewModel: TrainingViewModel
    override fun setHeaderFragment() {}
    lateinit var customAdapter: SlideTrainingParksAdapter
    lateinit var customvideoAdapter: SlideTrainingVideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog.show(parentFragmentManager,tagForBar)
        _viewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentTrainingBinding.inflate(inflater))
        setHeaderFragment()

        settingUpRecyclers()// inicializamos los recycler view
        observers()// inicializamos los observers para el modelo
        return binding.root
    }
    private fun loadParks(list: List<XPark>){
        customAdapter =  SlideTrainingParksAdapter(::itemClickListener,requireContext(), list)
        binding.lstParks.adapter = customAdapter
    }
    private fun loadVideos(list: List<VideoTraining>){
        customvideoAdapter =  SlideTrainingVideoAdapter(::itemVideoClickListener,requireContext(), list)
        binding.lstVideos.adapter = customvideoAdapter
    }
    private fun observers(){
        _viewModel.listParksTraining.observe(viewLifecycleOwner){
            loadingDialog.dismiss()
            loadParks(it)
            Log.i(tagForBar, "observers: parks $it")
        }
        _viewModel.listVideosTraining.observe(viewLifecycleOwner){
            Log.i(tagForBar, "observers: videos $it")
            loadVideos(it)
        }
    }
    fun settingUpRecyclers(){
        binding.lstParks.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.HORIZONTAL)
        binding.lstParks.setHasFixedSize(true)

        binding.lstVideos.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.HORIZONTAL)
        binding.lstVideos.setHasFixedSize(true)
    }

    override fun itemClickListener(item: XPark) {
        val bundle = Bundle()
        bundle.putString("xpark_name", item.name)
        bundle.putString("xpark_id", item.id.toString())
//        navigate(R.id.action_training_to_trainingDetail, bundle)//mandamos al detalle
    }

    override fun itemVideoClickListener(item: VideoTraining) {
        Log.i(tagForBar, "observers: video clicked $item")
//        val bundle = Bundle()
//        bundle.putString("xpark_name", item.name)
//        bundle.putString("xpark_id", item.id.toString())
//        navigate(R.id.action_training_to_trainingDetail, bundle)
    }

}
interface ParksTrainingListeners{
    fun itemClickListener(item: XPark): Unit
    fun itemVideoClickListener(item: VideoTraining): Unit
}
