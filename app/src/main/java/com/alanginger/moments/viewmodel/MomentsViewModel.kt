package com.alanginger.moments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alanginger.moments.model.Tweet
import com.alanginger.moments.model.User
import com.alanginger.moments.network.NetWorkManager
import com.alanginger.moments.services.MomentsService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MomentsViewModel : ViewModel() {

    private val momentsService = NetWorkManager.createService(MomentsService::class.java)
    private val mCompositeDisposable = CompositeDisposable()

    val userLiveData = MutableLiveData<User>()
    val tweetLiveData = MutableLiveData<List<Tweet>>()
    val tweetList = arrayListOf<Tweet>()

    /**
     * 获取用户信息
     */
    fun fetchUserInfo(userId: String) {
        momentsService.fetchUserInfo(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { user ->
                    userLiveData.value = user
                },
                onError = {
                    it.printStackTrace()
                }
            ).addTo(mCompositeDisposable)
    }

    /**
     * 获取Tweet流
     */
    fun fetchTweet(userId: String) {
        momentsService.fetchTweets(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { tweets ->
                    tweetList.clear()
                    tweets.forEach {
                        it.sender?.apply {
                            tweetList.add(it)
                        }
                    }
                    fetchTweetpagination()
                },
                onError = {
                    it.printStackTrace()
                }
            ).addTo(mCompositeDisposable)
    }

    /**
     * 模拟分页获取Tweet流
     */
    fun fetchTweetpagination(index: Int = 0) {
        Observable.create<List<Tweet>> {
            val pageData = arrayListOf<Tweet>()
            var i = 0
            while (index + i < tweetList.size && i < 5) {
                pageData.add(tweetList[index + i])
                i++
            }
            it.onNext(pageData)
        }.delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .subscribeBy {
                tweetLiveData.postValue(it)
            }.addTo(mCompositeDisposable)
    }

    /**
     * 刷新数据
     */
    fun refresh() {
        fetchTweet("jsmith")
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()

    }

}