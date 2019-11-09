package com.alanginger.moments

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alanginger.moments.viewmodel.MomentsViewModel
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_moments.*

class MomentsActivity : AppCompatActivity() {
    private lateinit var viewModel: MomentsViewModel

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

    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MomentsViewModel::class.java)

        viewModel.userLiveData.observe(this, Observer { user ->
            user ?: return@Observer
            tv_user_name.text = user.nick
            Glide.with(this@MomentsActivity)
                .load(user.profileImage)
                .error(R.drawable.error_background)
                .into(iv_moments_head)
            Glide.with(this@MomentsActivity)
                .load(user.avatar)
                .into(iv_user_avatar)
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
    }

    /**
     * 拉取数据
     */
    private fun fetchData() {
        viewModel.fetchUserInfo("jsmith")
    }
}
