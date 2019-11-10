package com.alanginger.moments.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alanginger.moments.R
import com.alanginger.moments.model.User
import com.alanginger.moments.view.TweetDivider
import com.alanginger.moments.viewmodel.MomentsViewModel
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_moments.*
import kotlinx.android.synthetic.main.moments_head.*

class MomentsActivity : AppCompatActivity() {
    private lateinit var viewModel: MomentsViewModel
    private lateinit var adapter: TweetAdapter
    private lateinit var momentsHead: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        setListener()
        fetchData()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_moments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        momentsHead = LayoutInflater.from(this).inflate(R.layout.moments_head, recyclerView, false)
        adapter = TweetAdapter()
        adapter.setHeaderView(momentsHead)
        adapter.bindToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(TweetDivider(ContextCompat.getDrawable(this,
            R.drawable.divider_line
        )!!))
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MomentsViewModel::class.java)

        viewModel.userLiveData.observe(this, Observer { user ->
            user ?: return@Observer
            updateMomentsHead(user)
        })

        viewModel.tweetLiveData.observe(this, Observer { tweets ->
            adapter.addData(tweets)
            if (adapter.data.size >= viewModel.tweetList.size)
                adapter.loadMoreEnd()
            else
                adapter.loadMoreComplete()
        })
    }

    /**
     * 设置Listener
     */
    private fun setListener() {
        btn_back.setOnClickListener {
            ToastUtils.showShort("你点击了返回按钮")
        }
        btn_camera.setOnClickListener {
            ToastUtils.showShort("你点击了发布按钮")
        }

        adapter.setOnLoadMoreListener({
            viewModel.fetchTweetpagination(adapter.data.size)
        }, recyclerView)

        refreshLayout.setOnRefreshListener {
            it.finishRefresh(1000)
            adapter.data.clear()
            adapter.notifyDataSetChanged()
            viewModel.refresh()
        }
    }

    /**
     * 拉取数据
     */
    private fun fetchData() {
        viewModel.fetchUserInfo("jsmith")
        viewModel.fetchTweet("jsmith")
    }

    /**
     * 更新朋友圈页头
     */
    private fun updateMomentsHead(user: User) {
        tv_user_name.text = user.nick
        Glide.with(this@MomentsActivity)
            .load(user.profileImage)
            .placeholder(R.drawable.error_background)
            .error(R.drawable.error_background)
            .into(iv_moments_head)
        Glide.with(this@MomentsActivity)
            .load(user.avatar)
            .into(iv_user_avatar)
    }
}
