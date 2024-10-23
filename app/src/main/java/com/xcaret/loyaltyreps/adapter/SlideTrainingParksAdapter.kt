package com.xcaret.loyaltyv2.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xcaret.loyaltyreps.R

import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.utils.Utils.getBitmapFromDrawableByName
import com.xcaret.loyaltyreps.databinding.ParkItemTrainingv3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
            var image: Int = R.drawable.parques
            when(xpark.name){
                "XCARET" -> {
                    image = R.drawable.xcaret
                }
                "XAILING CATAMARÁN" -> {
                    image = R.drawable.xailing_catamaran
                }
                "XAILING FERRY" -> {
                    image = R.drawable.xailing_ferry
                }
                "COBÁ" -> {
                    image = R.drawable.coba
                }
                "Actividades Extraordinarias" -> {
                    image = R.drawable.extraordinarias
                }
                "Parques" -> {
                    image = R.drawable.parques
                }
                "XPLOR FUEGO" -> {
                    image = R.drawable.xplor_fuego
                }
                "XICHÉN CLÁSICO" -> {
                    image = R.drawable.xichen_clasico
                }
                "XAVAGE" -> {
                    image = R.drawable.xavage
                }
                "XENOTES" -> {
                    image = R.drawable.xenotes
                }
                "XICHÉN DELUXE" -> {
                    image = R.drawable.xichen_deluxe
                }
                "XENSES" -> {
                    image = R.drawable.xenses
                }
                "XOXIMILCO" -> {
                    image = R.drawable.xoximilco
                }
                "XPLOR" -> {
                    image = R.drawable.xplor
                }
                "XEL-HÁ" -> {
                    image = R.drawable.xelha
                }
                else ->{
                    image = R.drawable.parques
                }
            }
            val bitmap = getBitmapFromDrawableByName(context, image)
            bitmap?.let{
                itemBinding.imgTrainingItem.setImage(it)
            }


            Glide.with(context)
                .load(xpark.logo)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.parkLogo)
        }
    }

}