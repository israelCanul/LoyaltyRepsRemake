package com.xcaret.loyaltyreps.view.general.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.adapter.GallerySlideAdapter
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.databinding.FragmentGalleryBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel

class GalleryFragment(): BaseFragmentDataBinding<FragmentGalleryBinding>(){
    private val viewModel: MainViewModel by activityViewModels()
    override val tagForBar: String
        get() = "GalleryFragment"
    override fun setHeaderFragment() {}
    lateinit var images: List<GalleryItem>

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

    fun initAdapter() {
        binding.xParkSlideViewPager.adapter = GallerySlideAdapter(requireContext(),
            images, R.layout.slide_item_gallery, 0)

        val slideSize = binding.xParkSlideViewPager.adapter!!.count -1

        binding.xParkSlideViewPager.currentItem = position
        binding.slideIndicator.setupWithViewPager(binding.xParkSlideViewPager)
        binding.xParkSlideViewPager.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener {
            var thresholdOffset: Float = 0.5f
            var thresholdOffsetPixels: Int = 1
            var scrollStarted: Boolean = false
            var checkDirection: Boolean = false

            override fun onPageScrollStateChanged(state: Int) {
                if (!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollStarted = true
                    checkDirection = true
                } else {
                    scrollStarted = false
                }
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                if (checkDirection) {
                    if (thresholdOffset > positionOffset && positionOffsetPixels > thresholdOffsetPixels) {

                    } else{
                        if (position == slideSize && scrollStarted) {
                            setItem0()
                        }
                    }
                }
                checkDirection = false
            }

            override fun onPageSelected(position: Int) {
            }

        })
        binding.closeMe.setOnClickListener {
            popBackStack()
        }
        binding.downLoadImageSelected.setOnClickListener{
            //TODO: aqui se descarga

        }

    }

    private fun observers() {
        viewModel.gallerySelected.observe(viewLifecycleOwner){
            Log.i(tagForBar, "items: $it")
            images = it
            initAdapter()
        }
    }


    private fun setItem0() {
        binding.xParkSlideViewPager.setCurrentItem(0, false)
    }
}