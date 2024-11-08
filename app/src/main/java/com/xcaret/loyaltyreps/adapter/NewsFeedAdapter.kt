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
import com.xcaret.loyaltyreps.data.api.ResponseNewsFeed
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.entity.GalleryItem
import com.xcaret.loyaltyreps.data.utils.AppPreferences.formatStringToDate
import com.xcaret.loyaltyreps.databinding.GalleryItemBinding
import com.xcaret.loyaltyreps.databinding.NewsfeedItemBinding


class NewsFeedAdapter(
    val clickListener: (item : ResponseNewsFeed, position: Int) -> Unit,
    private val context: Context,
    val listItems:List<ResponseNewsFeed>
): RecyclerView.Adapter<NewsFeedAdapter.NewsFeedHolder>() {


    inner class NewsFeedHolder(val itemBinding: NewsfeedItemBinding ): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(context: Context, item: ResponseNewsFeed){
            Glide.with(context)
                .load(item.cover_img)
                .placeholder(R.drawable.xcaret)
                .apply(
                    RequestOptions().transform(
                        RoundedCorners(16)
                    )
                        .error(R.drawable.bg_rounden_white)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .centerCrop()
                .into( itemBinding.imgCoverNew)
            itemBinding.txtDescNew.text = item.title
            itemBinding.txtSubtitleNew.text = formatStringToDate(item.created_at!!)
//            itemBinding.imgItemGallery.contentDescription = item.name + " gallery item"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedHolder {
        val itemBinding = NewsfeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFeedHolder(itemBinding)
    }
    override fun getItemCount(): Int = listItems.count()

    override fun onBindViewHolder(holder: NewsFeedHolder, position: Int) {
        val item = listItems[position]
        holder.bind(context, item)
        holder.itemBinding.cardItemGallery.setOnClickListener{
            clickListener(item, position)
        }

    }
}