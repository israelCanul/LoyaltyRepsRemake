package com.xcaret.loyaltyreps.view.newsfeed.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.adapter.NewsFeedAdapter
import com.xcaret.loyaltyreps.data.api.ResponseNewsFeed
import com.xcaret.loyaltyreps.data.utils.AppPreferences.formatStringToDate
import com.xcaret.loyaltyreps.databinding.FragmentNewsfeedBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.GeneralViewModel
import com.xcaret.loyaltyreps.view.newsfeed.vm.FeedsViewModel


class NewsFeed () : BaseFragmentDataBinding<FragmentNewsfeedBinding>(){
    private val viewModel: GeneralViewModel by activityViewModels()
    lateinit var _viewModel: FeedsViewModel
    override val tagForBar: String
        get() = "NewsFeed"
    override fun setHeaderFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModel = ViewModelProvider(this)[FeedsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentNewsfeedBinding.inflate(inflater))
        setHeaderFragment()
        settingUpRecyclers()
        observers()

        return binding.root
    }

    private fun observers() {
        _viewModel.newsFeedresponse.observe(viewLifecycleOwner){
            it?.let{
                loadingDialog.dismiss()
                listeners()
                it[0].let{ it1 ->
                    binding.txtTitleNew.text = it1.title
                    binding.txtSubtitleNew.text = it1.created_at?.let { it2 ->
                        formatStringToDate(
                            it2
                        )
                    }

                    Glide.with(requireContext())
                        .load(it1.cover_img)
                        .placeholder(R.drawable.xcaret)
                        .apply(
                            RequestOptions().transform(
                                RoundedCorners(16)
                            )
                                .error(R.drawable.bg_rounden_white)
                                .skipMemoryCache(false)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .centerCrop()
                        .into( binding.imgFirstNew)
                }

                val updatedList = it.drop(1)
                binding.rvNewsList.adapter = NewsFeedAdapter(::itemClicked, requireContext(), updatedList)
            }?:run{
                loadingDialog.show(parentFragmentManager, tagForBar )
            }
        }
    }

    fun settingUpRecyclers(){
        binding.rvNewsList.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        binding.rvNewsList.setHasFixedSize(true)
    }
    fun itemClicked(item : ResponseNewsFeed, position: Int){
        Log.i(tagForBar, "itemClicked: $item")
        viewModel.setNewSelected(item)
        navigate(R.id.action_newsFeed_to_newsDetail, Bundle.EMPTY)
    }

    private fun listeners() {
        binding.cardFirstNew.setOnClickListener {
            val item = _viewModel.newsFeedresponse.value?.get(0)
            if (item != null) {
                itemClicked(item, 0)
            }
        }
    }
}