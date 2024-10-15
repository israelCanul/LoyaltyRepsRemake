package com.xcaret.loyaltyreps.view.training.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.adapter.QuestionFragment
import com.xcaret.loyaltyreps.adapter.QuestionsViewPagerAdapter
import com.xcaret.loyaltyreps.data.api.TrainingSection
import com.xcaret.loyaltyreps.data.api.VideoQuizQuestion
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.databinding.FragmentParkTrainingBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingDetailsParkBinding

import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.training.vm.TrainingViewModel

class ParkTrainingView:  BaseFragmentDataBinding<FragmentParkTrainingBinding>(),ParksDetailTrainingListeners{
    override val tagForBar: String
        get() = "ParkTrainingView"
    lateinit var _viewModel: TrainingViewModel

    override fun setHeaderFragment() {}
    lateinit var parkName: String
    lateinit var parkId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parkName = arguments?.getString("xpark_name")!!.toString()
        parkId = arguments?.getString("xpark_id")!!.toString()
        _viewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        //get the info of the park selected
        _viewModel.getParkTraining(parkId)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentParkTrainingBinding.inflate(inflater))
        setHeaderFragment()

        listeners()
        observers()

        return binding.root
    }

    private fun settingUpUi(trainingParkInfo: TrainingSection) {
        val fragments: MutableList<Fragment> = mutableListOf()

        fragments.add(TrainingDetailsPark(trainingParkInfo, ::itemVideoClickListener, ::itemVideoDownloadListener))
        fragments.add(TrainingExtrasPark(trainingParkInfo, ::navigate))
        fragments.add(TrainingGalleryPark())

        val adapter = QuestionsViewPagerAdapter(fragments, this)
        binding.fpTrainingPark.adapter = adapter
        binding.fpTrainingPark.isUserInputEnabled = true
        binding.fpTrainingPark.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.i(tagForBar, "onPageSelected: $position")

                binding.btnPark.background =  ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_white_border_black)
                binding.btnPark.setTextColor(Color.BLACK)
                binding.btnPark.setOnClickListener {
                    binding.fpTrainingPark.setCurrentItem(0, true)
                }
                binding.btnExtras.background =  ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_white_border_black)
                binding.btnExtras.setTextColor(Color.BLACK)
                binding.btnExtras.setOnClickListener {
                    binding.fpTrainingPark.setCurrentItem(1, true)
                }
                binding.btnGallery.background =  ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_white_border_black)
                binding.btnGallery.setTextColor(Color.BLACK)
                binding.btnGallery.setOnClickListener {
                    binding.fpTrainingPark.setCurrentItem(2, true)
                }
                when(position){
                    0 ->{
                        binding.btnPark.background =  ContextCompat.getDrawable(requireContext(), R.drawable.button_green)
                        binding.btnPark.setTextColor(Color.WHITE)
                    }
                    1 ->{
                        binding.btnExtras.background =  ContextCompat.getDrawable(requireContext(), R.drawable.button_green)
                        binding.btnExtras.setTextColor(Color.WHITE)
                    }
                    2 ->{
                        binding.btnGallery.background =  ContextCompat.getDrawable(requireContext(), R.drawable.button_green)
                        binding.btnGallery.setTextColor(Color.WHITE)
                    }
                }
            }
        })
    }


    private fun observers() {
        _viewModel.parkTrainingDetail.observe(viewLifecycleOwner){
            it?.let {
                if(loadingDialog.isAdded) loadingDialog.dismiss()
                binding.txtTitle.text = parkName
                Log.i(tagForBar, "observers: $it")
                settingUpUi(it)
            }?: run{
                if(!loadingDialog.isAdded){
                    loadingDialog.show(parentFragmentManager, "tagForBar")
                }
            }
        }
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener{
            popBackStack()
        }
    }
    override fun itemVideoClickListener(urlVideo: String) {
        val bundle = Bundle()
        bundle.putString("xvideo_url", urlVideo)
        navigate(R.id.fullscreenVideoPlayer, bundle)
    }
    override fun itemVideoDownloadListener(urlVideo: String) {
        _viewModel.saveVideo(requireContext(),requireActivity(), urlVideo, parkName)
    }
}