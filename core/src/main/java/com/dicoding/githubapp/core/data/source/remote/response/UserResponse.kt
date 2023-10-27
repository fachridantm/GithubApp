package com.dicoding.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("total_count")
    val amount: Int? = 0,

    @SerializedName("items")
    val items: List<UserItem>? = null
)