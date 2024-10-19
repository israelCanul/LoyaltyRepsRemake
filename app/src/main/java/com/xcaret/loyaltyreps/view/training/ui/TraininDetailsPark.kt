package com.xcaret.loyaltyreps.view.training.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.adapter.GalleryAdapter
import com.xcaret.loyaltyreps.data.api.TrainingDetail
import com.xcaret.loyaltyreps.data.api.TrainingImagesSection
import com.xcaret.loyaltyreps.data.api.TrainingSection
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.databinding.FragmentTrainingDetailsParkBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingExtrasParkBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingGalleryParkBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel


class TrainingDetailsPark(private val parkTraining : TrainingSection,private val  playVideo: (url : String)-> Unit,private val downloadVideo: (url : String)-> Unit): Fragment(){
    private var _binding: FragmentTrainingDetailsParkBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingDetailsParkBinding.inflate(inflater)

        binding.txtParkDescription.text = HtmlCompat.fromHtml(parkTraining.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        Glide.with(this)
            .load(parkTraining.cover_img)
            .centerCrop()
            .apply(
                RequestOptions().transform(
                    RoundedCorners(16)
                )
                    .error(R.drawable.bg_rounden_white)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.ivVideoTrainingThumbnail)
        binding.ivVideoTrainingPlay.setOnClickListener{
            playVideo(parkTraining.video)
        }
        binding.btnDownloadVideoTrainingPark.setOnClickListener{
            downloadVideo(parkTraining.video)
        }
        return binding.root
    }

}
class TrainingExtrasPark(private val parkTraining : TrainingSection,private val navigate: (id: Int, args: Bundle) ->Unit ): Fragment(){
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentTrainingExtrasParkBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingExtrasParkBinding.inflate(inflater)


        renderInfoPark(parkTraining)

        return binding.root
    }
    fun renderInfoPark(trainingSection: TrainingSection){
        binding.detailsTablelayout.removeAllViews()
        trainingSection.training_details.forEach(){
            creatTableItem(it)
        }
    }
    private fun creatTableItem(xTraining: TrainingDetail){
        val inflater2 = this.layoutInflater
        val item_row2 = inflater2.inflate(R.layout.tablerow_training_park_extra, null)

        item_row2.findViewById<TextView>(R.id.gotoDetails).text = xTraining.name
        item_row2.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("xtraining_id", xTraining.id!!)
            bundle.putString("xtraining_detail", xTraining.description)
            bundle.putString("xtraining_name", xTraining.name)
            viewModel.setTrainingDetail(xTraining)
            navigate(R.id.action_parkTrainingView_to_trainingExtrasParkDetailScreen, bundle)
        }
        binding.detailsTablelayout.addView(item_row2)
    }
}
class TrainingGalleryPark(private val parkTraining : TrainingSection,private val navigate: (id: Int, args: Bundle) ->Unit ): Fragment(), GalleryListeners{
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentTrainingGalleryParkBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingGalleryParkBinding.inflate(inflater)

        settingUpRecyclers()


        settingUpItemsToGallery(parkTraining.images)

        return binding.root
    }

    private fun settingUpItemsToGallery(images: List<TrainingImagesSection>) {
        val itemsGallery = mutableListOf<GalleryItem>()
        images.forEach{
            itemsGallery.add(GalleryItem(it.name, it.image, it.name))
        }
        viewModel.setTrainingSection(itemsGallery)// se agregan los items al mainViewModel
        val customAdapter = GalleryAdapter(::itemClickListener, requireContext(),itemsGallery )
        binding.menuItemsGalleryRecyclerView.adapter = customAdapter
    }

    fun settingUpRecyclers(){
        binding.menuItemsGalleryRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        binding.menuItemsGalleryRecyclerView.setHasFixedSize(true)
    }

    override fun itemClickListener(item: GalleryItem, position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)
        bundle.putString("gallery_name", "prueba")

        navigate(R.id.action_parkTrainingView_to_galleryFragment, bundle)
    }
}
interface GalleryListeners{
    fun itemClickListener(item: GalleryItem, position: Int = 0): Unit
}

interface ParksDetailTrainingListeners{
    fun itemVideoClickListener(urlVideo: String): Unit
    fun itemVideoDownloadListener(urlVideo: String): Unit
}