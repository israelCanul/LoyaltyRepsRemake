package com.xcaret.loyaltyreps.view.newsfeed.ui

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.ResponseNewsFeed
import com.xcaret.loyaltyreps.data.utils.AppPreferences.formatStringToDate
import com.xcaret.loyaltyreps.databinding.FragmentNewsfeedBinding
import com.xcaret.loyaltyreps.databinding.FragmentNewsfeedDetailBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.GeneralViewModel
import com.xcaret.loyaltyreps.view.newsfeed.vm.FeedsViewModel

class NewsDetail () : BaseFragmentDataBinding<FragmentNewsfeedDetailBinding>(){
    private val viewModel: GeneralViewModel by activityViewModels()
    lateinit var _viewModel: FeedsViewModel
    override val tagForBar: String
        get() = "NewsDetail"

    override fun setHeaderFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[FeedsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentNewsfeedDetailBinding.inflate(inflater))
        setHeaderFragment()
        observers()
        listeners()
        return binding.root
    }

    private fun observers() {
        viewModel.newsSelected.observe(viewLifecycleOwner){
            it?.let { it1 -> loadNewByInfo(it1) }
        }
    }

    private fun loadNewByInfo(newsFeed: ResponseNewsFeed)  {

        Glide.with(requireContext())
            .load(newsFeed.cover_img)
            .placeholder(R.drawable.xcaret)
            .apply(
                RequestOptions().transform(
                    RoundedCorners(16)
                )
                    .error(R.drawable.bg_rounden_white)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
            .centerCrop()
            .into(binding.imgNewsDetail)

        binding.txtTitleNew.text = newsFeed.title
        binding.txtSubtitleNew.text = newsFeed.created_at?.let { it2 ->
            formatStringToDate(
                it2
            )
        }
        val spannedText: Spanned = HtmlCompat.fromHtml(newsFeed.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.txtDescNew.text = spannedText
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            popBackStack()
        }
    }
}