package com.alanginger.moments.ui

import android.view.View
import com.alanginger.moments.R
import com.alanginger.moments.model.Tweet
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import kotlinx.android.synthetic.main.tweet_item.view.*


class TweetItemProvider : BaseItemProvider<Tweet, BaseViewHolder>() {

    companion object {
        const val TWEET_TYPE = 1
    }

    override fun layout(): Int {
        return R.layout.tweet_item
    }

    override fun viewType(): Int {
        return TWEET_TYPE
    }

    override fun convert(helper: BaseViewHolder, tweet: Tweet?, position: Int) {
        tweet ?: return
        Glide.with(helper.itemView.context)
            .load(tweet.sender?.avatar)
            .placeholder(R.color.bg_gray)
            .error(R.color.bg_gray)
            .into(helper.itemView.iv_tweet_avatar)
        helper.setGone(R.id.layout_image, tweet.images?.size ?: 0 > 0)
        helper.itemView.tv_tweet_name.text = tweet.sender?.nick
        helper.itemView.tv_tweet_content.text = tweet.content
        if (tweet.images?.size ?: 0 > 0) {
            helper.itemView.layout_image.render(tweet.images)
            helper.itemView.layout_image.visibility = View.VISIBLE
        } else {
            helper.itemView.layout_image.visibility = View.GONE
        }
        if (tweet.comments?.size ?: 0 > 0) {
            helper.itemView.comments_view.setData(tweet.comments)
            helper.itemView.comments_view.visibility = View.VISIBLE
        } else {
            helper.itemView.comments_view.visibility = View.GONE
        }
    }

}