package com.alanginger.moments.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.alanginger.moments.R
import com.alanginger.moments.model.Image
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageNineGridLayout(context: Context, attrs: AttributeSet) :
    AbstractNineGridLayout<Image>(context, attrs) {

    private var mClickListener: ImageNineGridViewClickListener? = null
    private var mImageViews: Array<ImageView>? = null

    override fun fill() {
        fill(R.layout.image_nine_grid_item)
        mImageViews = findInChildren(R.id.image_item, ImageView::class.java)
    }

    override fun render(images: MutableList<Image>?) {
        images ?: return
        setDisplayCount(images.size)
        for (i in images.indices) {
            if (i >= 9) {
                return
            }
            val url = images[i].url
            mImageViews?.get(i)?.let {
                Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.placeholderOf(placeHolder))
                    .into(it)
                it.setOnClickListener(OnImageViewClickListener(i))
            }
        }
    }

    private inner class OnImageViewClickListener(private val index: Int) : OnClickListener {

        override fun onClick(v: View) {
            mClickListener?.onImageViewClick(v, index)
        }
    }

    interface ImageNineGridViewClickListener {
        fun onImageViewClick(onClickView: View, index: Int)
    }

    fun setImageNineGridViewClickListener(listener: ImageNineGridViewClickListener) {
        mClickListener = listener
    }
}