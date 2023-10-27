package com.dicoding.githubapp.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(

    @SerializedName("total_count")
    val amount: Int? = 0,

    @SerializedName("items")
    val items: List<UserItem>? = null
)