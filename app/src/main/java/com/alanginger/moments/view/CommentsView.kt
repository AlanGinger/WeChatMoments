package com.alanginger.moments.view

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.alanginger.moments.R
import com.alanginger.moments.model.Comment

class CommentsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    var mComments: List<Comment>? = null

    private var mLayoutInflater: LayoutInflater? = null

    fun setData(datas: List<Comment>?) {
        datas ?: return
        mComments = datas
        notifyDataSetChanged()
    }

    private fun notifyDataSetChanged() {
        removeAllViews()
        mComments?.let {
            for (i in it.indices) {
                val view = getView(i)
                addView(view)
            }
        }
    }

    private fun getView(position: Int): View {
        if (null == mLayoutInflater) {
            mLayoutInflater = LayoutInflater.from(context)
        }
        val convertView = mLayoutInflater!!.inflate(R.layout.layout_comment_item, null, false)
        val txtComment = convertView.findViewById<TextView>(R.id.tv_comment)
        val comment = mComments?.get(position)
        val username = comment?.sender?.nick
        val content = comment?.content

        val sb = SpannableStringBuilder()
        sb.append(username!! + ": ")
        sb.append(content)
        sb.setSpan(
            ForegroundColorSpan(Color.parseColor("#1e90ff")),
            0, username.length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        txtComment.text = sb

        return convertView

    }
}
