package com.alanginger.moments.services

import com.alanginger.moments.model.Tweet
import com.alanginger.moments.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MomentsService {

    @GET("user/{userId}")
    fun fetchUserInfo(
        @Path("userId") userId: String
    ):Observable<User>

    @GET("user/{userId}/tweets")
    fun fetchTweets(
        @Path("userId") userId: String
    ):Observable<List<Tweet>>
}