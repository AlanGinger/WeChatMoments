package com.alanginger.moments.model

class Tweet {
    var comments: List<Comment>? = null
    var content: String = ""
    var images: List<Image>? = null
    var sender: User? = null
    var error: String? = null
}

data class Comment(
    var content: String,
    var sender: User
)

data class Image(
    var url: String
)

