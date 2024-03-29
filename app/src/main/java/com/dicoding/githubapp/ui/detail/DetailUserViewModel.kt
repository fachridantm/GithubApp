package com.dicoding.githubapp.ui.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import com.dicoding.githubapp.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    fun setFavoriteUsers(user: User, state: Boolean, context: Context) =
        viewModelScope.launch {
            userUseCase.setFavoriteUsers(user, state)
            Event(
                if (state) {
                    context.getString(R.string.add_favorite, user.username)
                } else {
                    context.getString(R.string.remove_favorite, user.username)
                }
            ).also { _toast.value = it }
        }

    fun getFavoritedUsers() = userUseCase.getFavoritedUsers().asLiveData()

    fun getDetailUser(username: String) = userUseCase.getDetailUser(username).asLiveData()

    fun getUserFollowers(username: String) = userUseCase.getUserFollowers(username).asLiveData()

    fun getUserFollowing(username: String) = userUseCase.getUserFollowing(username).asLiveData()


}