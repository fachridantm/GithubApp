package com.dicoding.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserItem(

    @SerializedName("login")
    val username: String,

    @SerializedName("company")
    val company: String? = null,

    @SerializedName("public_repos")
    val repository: Int,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("html_url")
    val url: String,

    @SerializedName("following")
    val following: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("location")
    val location: String? = null
)
