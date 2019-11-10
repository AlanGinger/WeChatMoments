package com.alanginger.moments.ui

import com.alanginger.moments.model.Tweet
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter

class TweetAdapter : MultipleItemRvAdapter<Tweet, BaseViewHolder>(null) {
    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(EmptyItemProvider())
        mProviderDelegate.registerProvider(TweetItemProvider())
    }

    override fun getViewType(t: Tweet?): Int {
        t?.sender?.let {
            return TweetItemProvider.TWEET_TYPE
        } ?: run {
            return EmptyItemProvider.DEFAULT_TYPE
        }
    }

}