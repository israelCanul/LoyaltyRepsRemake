package com.xcaret.loyaltyreps.view.parks.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.adapter.GalleryAdapter
import com.xcaret.loyaltyreps.adapter.ParkLanguageAdapter
import com.xcaret.loyaltyreps.data.api.TrainingImagesSection
import com.xcaret.loyaltyreps.data.api.XParkInfographic
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.data.utils.Utils.getParkLogoBitmapFromDrawableByName
import com.xcaret.loyaltyreps.databinding.FragmentParkDetailBinding
import com.xcaret.loyaltyreps.databinding.FragmentParksBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel
import com.xcaret.loyaltyreps.view.parks.vm.ParksViewModel

class ParkDetail() : BaseFragmentDataBinding<FragmentParkDetailBinding>(), ParkLanguageListeners {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var _viewModel: ParksViewModel
    lateinit var infographics: List<XParkInfographic>
    override val tagForBar: String
        get() = "ParksDetail"

    lateinit var parkName: String
    lateinit var parkId: String
    override fun setHeaderFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parkName = arguments?.getString("xpark_name")?: "Sample"
        parkId = arguments?.getString("xpark_id")?: "100"

        _viewModel = ViewModelProvider(this)[ParksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentParkDetailBinding.inflate(inflater))
        setHeaderFragment()
        settingUpRecyclers()



        val bitmapLogo = getParkLogoBitmapFromDrawableByName(requireContext(), parkName)
        bitmapLogo?.let{
            binding.ivParkLogo.setImageBitmap(it)
        }

        observers()
        listeners()

        return binding.root
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            popBackStack()
        }
    }

    private fun observers() {
        viewModel.parkSelected.observe(viewLifecycleOwner){
            it?.let {
                Log.i(tagForBar, "itemClickListener: $it")
                settingUpItemsToGallery(it.infographics)
                infographics = it.infographics
            }
        }
    }

    fun settingUpRecyclers(){
        binding.menuItemsLanguageRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        binding.menuItemsLanguageRecyclerView.setHasFixedSize(true)
    }

    private fun settingUpItemsToGallery(languages: List<XParkInfographic>) {
        val itemsLanguage  = mutableListOf<XParkInfographic>()
        val languagesSelected = mutableListOf<String>()
        languages.forEach{
            if(!languagesSelected.contains(it.language)){
                languagesSelected.add(it.language)
                itemsLanguage.add(it)
            }
        }
        val customAdapter = ParkLanguageAdapter(::itemClickListener, requireContext(),itemsLanguage )
        binding.menuItemsLanguageRecyclerView.adapter = customAdapter
    }

    override fun itemClickListener(language: String) {
        Log.i(tagForBar, "itemClickListener: $language selected")
        val itemsGallery = mutableListOf<GalleryItem>()
        infographics.forEach{
            if(it.language == language){
                itemsGallery.add(GalleryItem(it.language, it.image, it.language))
            }
        }
        viewModel.setTrainingSection(itemsGallery)
        val bundle = Bundle()
        bundle.putInt("position", 0)
        bundle.putString("gallery_name", language)

        navigate(R.id.galleryFragment, bundle)
    }
}
interface ParkLanguageListeners{
    fun itemClickListener(language: String): Unit
}