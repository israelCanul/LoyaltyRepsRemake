package com.xcaret.loyaltyv2.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.VideoTraining

import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.utils.Session
import com.xcaret.loyaltyreps.databinding.ItemVideoTrainingBinding


class SlideTrainingVideoAdapter(
    val clickListener: (VideoTraining) -> Unit,
    private val context: Context,
    val listItems:List<VideoTraining>
): RecyclerView.Adapter<SlideTrainingVideoAdapter.TrainingVideoHolder>() {
    lateinit var result: List<Int>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingVideoHolder {
        val itemBinding = ItemVideoTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        if (Session.getQuizzesId(getApp().mContext).isEmpty()) {
            result = Session.getQuizzesId(getApp().mContext).split(",").map(String::toInt)
        } else {
            result = listOf(0)
        }

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
                    RequestOptions().transform(RoundedCorners(16)
                    )
                    .error(R.drawable.bg_rounden_white)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(itemBinding.imgTrainingVideoCover)


            if (result.isNotEmpty()) {
                for (item in result){
                    if (item == video.quiz_id) {
                        itemBinding.btnQuizQuizzVideo.visibility = View.GONE
                        itemBinding.availabilityContainerQuizzVideo.visibility = View.GONE
//                        holder.quiz_completed.visibility = View.VISIBLE
                    }
                }
            }

//            if (listOfQuizIds.isNotEmpty()) {
//                for (item in listOfQuizIds){
//                    if (item == xvideo.quiz_id) {
//                        holder.video_button.visibility = View.GONE
//                        holder.video_avalability_con.visibility = View.GONE
//                        holder.quiz_completed.visibility = View.VISIBLE
//                    }
//                }
//            }

//            if (!xvideo.quiz_available) {
//                holder.video_button.background = ContextCompat.getDrawable(context, R.drawable.button_disabled)
//            } else {
//                holder.video_button.setOnClickListener {
//                    loadVideoQuizData(xvideo.id.toString(), xvideo.name!!, holder.video_button)
//                }
//            }
//            if(xvideo.active!!){
//                holder.download_video.setOnClickListener {
//                    var dm : DownloadImage = DownloadImage()
//                    mydownloadID = dm.saveVideo(context!!,activity,xvideo.video.toString(),xvideo.name!! + "_quizz")
//                }
//            }
//            holder.vide_cover.setOnClickListener {
//                val bundle = Bundle().also {
//                    it.putString("xvideo_url", xvideo.video)
//                    it.putString("video_id", "1")
//                }
//                holder.itemView.findNavController().navigate(R.id.to_XVideoActivity, bundle)
//            }


        }
    }
}