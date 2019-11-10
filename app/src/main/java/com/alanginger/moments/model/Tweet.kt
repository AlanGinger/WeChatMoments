package com.alanginger.moments.model

class Tweet {
    var comments: List<Comment>? = null
    var content: String = ""
    var images: MutableList<Image>? = null
    var sender: User? = null
    var error: String? = null
}





