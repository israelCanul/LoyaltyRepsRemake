package com.xcaret.loyaltyreps.adapter

import android.content.Context
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
import com.xcaret.loyaltyreps.data.api.XParkInfographic
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.databinding.GalleryItemBinding
import com.xcaret.loyaltyreps.databinding.ParkLanguageItemBinding


class ParkLanguageAdapter(
    private val clickListener: (language : String) -> Unit,
    private val context: Context,
    val listItems:List<XParkInfographic>
): RecyclerView.Adapter<ParkLanguageAdapter.ParkLanguageHolder>() {

    inner class ParkLanguageHolder(val itemBinding: ParkLanguageItemBinding ): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, item: XParkInfographic){
            itemBinding.txtParkInfoLanguage.text = item.language
            itemBinding.txtParkInfoImage.setImageResource(R.drawable.flag_english)
            itemBinding.txtParkInfoImage
           itemBinding.containerLanguage.setOnClickListener{
               clickListener(item.language)
           }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkLanguageHolder {
        val itemBinding = ParkLanguageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParkLanguageHolder(itemBinding)
    }
    override fun getItemCount(): Int = listItems.count()

    override fun onBindViewHolder(holder: ParkLanguageHolder, position: Int) {
        val item = listItems[position]
        holder.bind(context, item)
//        holder.itemBinding.cardItemGallery.setOnClickListener{
//            clickListener(item, position)
//        }

    }
}