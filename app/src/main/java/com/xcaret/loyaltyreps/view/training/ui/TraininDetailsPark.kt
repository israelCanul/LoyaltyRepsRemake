package com.xcaret.loyaltyreps.view.training.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.TrainingSection
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.databinding.FragmentTrainingDetailsParkBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingExtrasParkBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingGalleryParkBinding


class TrainingDetailsPark(private val parkTraining : TrainingSection,private val  playVideo: (url : String)-> Unit): Fragment(){
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
//iv_video_training_play
        binding.ivVideoTrainingPlay.setOnClickListener{
            playVideo(parkTraining.video)
        }
        return binding.root
    }

}
class TrainingExtrasPark(): Fragment(){
    private var _binding: FragmentTrainingExtrasParkBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingExtrasParkBinding.inflate(inflater)
        return binding.root
    }
}
class TrainingGalleryPark(): Fragment(){
    private var _binding: FragmentTrainingGalleryParkBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingGalleryParkBinding.inflate(inflater)
        return binding.root
    }
}