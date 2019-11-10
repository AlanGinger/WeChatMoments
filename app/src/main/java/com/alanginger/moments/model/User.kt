package com.alanginger.moments.model

import com.alibaba.fastjson.annotation.JSONField

data class User(
    var avatar: String?,
    var nick: String?,
    @JSONField(name = "profile-image")
    var profileImage: String?,
    var username: String?
)