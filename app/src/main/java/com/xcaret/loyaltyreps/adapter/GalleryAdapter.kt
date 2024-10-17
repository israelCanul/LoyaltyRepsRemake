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
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.databinding.GalleryItemBinding


class GalleryAdapter(
    val clickListener: (item : GalleryItem) -> Unit,
    private val context: Context,
    val listItems:List<GalleryItem>
): RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    inner class GalleryHolder(val itemBinding: GalleryItemBinding ): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, item: GalleryItem){
            Glide.with(context)
                .load(item.img)
                .centerCrop()
                .apply(
                    RequestOptions().transform(
                        RoundedCorners(16)
                    )
                        .error(R.drawable.bg_rounden_white)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into( itemBinding.imgItemGallery)
            itemBinding.imgItemGallery.contentDescription = item.name + " gallery item"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        val itemBinding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryHolder(itemBinding)
    }
    override fun getItemCount(): Int = listItems.count()

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        val item = listItems[position]
        holder.bind(context, item)
        holder.itemBinding.cardItemGallery.setOnClickListener{
            clickListener(item)
        }

    }
}