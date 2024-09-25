package com.xcaret.loyaltyv2.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.databinding.ParkItemTrainingv3Binding


class SlideTrainingParksAdapter(
    val clickListener: (XPark) -> Unit,
    private val context: Context,
    val listItems:List<XPark>
): RecyclerView.Adapter<SlideTrainingParksAdapter.TrainingParksHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingParksHolder {
        val itemBinding = ParkItemTrainingv3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingParksHolder(itemBinding)
    }
    override fun getItemCount() = listItems.size
    override fun onBindViewHolder(holder: TrainingParksHolder, position: Int) {
        var park = listItems[position]
        holder.bind(context, park)
        holder.itemBinding.containerParkTraining.setOnClickListener {
            clickListener(park)
        }
    }
    inner class TrainingParksHolder(val itemBinding: ParkItemTrainingv3Binding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, xpark: XPark){
//            Log.i("ParkTraining", "$xpark")

            Glide.with(context)
                .load(xpark.logo)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.parkLogo)
        }
    }
}