package com.dicoding.githubapp.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FollowItem(

    @SerializedName("following_url")
    val followingUrl: String,

    @SerializedName("login")
    val username: String,

    @SerializedName("followers_url")
    val followersUrl: String,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("html_url")
    val url: String,
)
