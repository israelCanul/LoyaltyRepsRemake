package com.xcaret.loyaltyv2.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.VideoTraining

import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.databinding.ItemVideoTrainingBinding


class SlideTrainingVideoAdapter(
    val clickListener: (VideoTraining) -> Unit,
    private val context: Context,
    val listItems:List<VideoTraining>
): RecyclerView.Adapter<SlideTrainingVideoAdapter.TrainingVideoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingVideoHolder {
        val itemBinding = ItemVideoTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingVideoHolder(itemBinding)
    }
    override fun getItemCount() = listItems.size
    override fun onBindViewHolder(holder: TrainingVideoHolder, position: Int) {
        var video = listItems[position]
        holder.bind(context, video)
        holder.itemBinding.containerVideoTraining.setOnClickListener {
            clickListener(video)
        }
    }
    inner class TrainingVideoHolder(val itemBinding: ItemVideoTrainingBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, video: VideoTraining){
            Log.i("ParkTraining", "$video")
            itemBinding.nameQuizzVideo.text = video.name
            itemBinding.pointsQuizzVideo.text = video.points.toString()
            itemBinding.dealineQuizzVideo.text = video.deadline
//            itemBinding.parkTitle.text = xpark.name
//            itemBinding.parkTitle.setTextColor(Color.parseColor(xpark.color!!))
            Glide.with(context)
                .load(video.cover_img)
                .fitCenter()
                .apply(
                    RequestOptions().transform(RoundedCorners(16))
                    .error(R.drawable.bg_rounden_white).skipMemoryCache(true).diskCacheStrategy(
                            DiskCacheStrategy.ALL))
                .into(itemBinding.imgTrainingVideoCover)
        }
    }
}