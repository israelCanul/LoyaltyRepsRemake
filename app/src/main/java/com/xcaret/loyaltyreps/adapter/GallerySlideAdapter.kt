package com.xcaret.loyaltyreps.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.entity.GalleryItem

class GallerySlideAdapter(
    var context: Context,
    private var images: List<GalleryItem>,
    var resource: Int ?= null,
    var origin: Int? = null,
    var park: String? = null
): PagerAdapter() {
    //
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return images.size
    }
//
    override fun instantiateItem(container: ViewGroup, position: Int): Any{
        val image = images[position] //[position]
        val view = LayoutInflater.from(container.context).inflate(resource!!, container,
            false)
//

            val imageViewItem: PhotoView = view.findViewById(R.id.slideFullImage)
            Glide.with(context)
                .load(image.img)
                .fitCenter()
                .into(imageViewItem)


        container.addView(view)
//
        return view
    }
//
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}