package com.xcaret.loyaltyv2.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
//            itemBinding.parkTitle.text = xpark.name
//            itemBinding.parkTitle.setTextColor(Color.parseColor(xpark.color!!))
            Glide.with(context).load(video.cover_img).into(itemBinding.imgTrainingVideoCover)
        }
    }
}