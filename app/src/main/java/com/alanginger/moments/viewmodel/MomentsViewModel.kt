package com.alanginger.moments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alanginger.moments.model.User
import com.alanginger.moments.network.NetWorkManager
import com.alanginger.moments.services.MomentsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class MomentsViewModel : ViewModel() {

    private val momentsService = NetWorkManager.createService(MomentsService::class.java)
    private val mCompositeDisposable = CompositeDisposable()

    val userLiveData = MutableLiveData<User>()

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

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()

    }

}