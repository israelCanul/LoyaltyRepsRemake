package com.xcaret.loyaltyv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.utils.Utils.getParkBitmapFromDrawableByName
import com.xcaret.loyaltyreps.data.utils.Utils.getParkLogoBitmapFromDrawableByName
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
            val bitmap = getParkBitmapFromDrawableByName(context, xpark.name)
            bitmap?.let{
                itemBinding.imgTrainingItem.setImage(it)
            }
            val bitmapLogo = getParkLogoBitmapFromDrawableByName(context, xpark.name)
            bitmapLogo?.let{
                itemBinding.parkLogo.setImageBitmap(it)
            }
        }
    }

}