package com.xcaret.loyaltyreps.view.general.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.databinding.FragmentGalleryBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel

class GalleryFragment(): BaseFragmentDataBinding<FragmentGalleryBinding>(){
    private val viewModel: MainViewModel by activityViewModels()
    override val tagForBar: String
        get() = "GalleryFragment"
    override fun setHeaderFragment() {}
    lateinit var images: ArrayList<GalleryItem>

    var position: Int = 0
    lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentGalleryBinding.inflate(inflater))
        position = arguments?.getInt("position")!!
        name = arguments?.getString("gallery_name")!!

        Log.i(tagForBar, "onCreateView: $position $name")

        observers()

        return binding.root
    }

    private fun observers() {
        viewModel.gallerySelected.observe(viewLifecycleOwner){
            Log.i(tagForBar, "items: $it")
        }
    }


}