package com.alanginger.moments.ui

import com.alanginger.moments.R
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider


class EmptyItemProvider : BaseItemProvider<Any, BaseViewHolder>() {

    companion object {
        const val DEFAULT_TYPE = 0
    }

    override fun layout(): Int {
        return R.layout.empty_list_item
    }

    override fun viewType(): Int {
        return DEFAULT_TYPE
    }

    override fun convert(helper: BaseViewHolder, data: Any?, position: Int) {
    }

}