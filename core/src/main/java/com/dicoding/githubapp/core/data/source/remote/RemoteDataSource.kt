package com.dicoding.githubapp.core.data.source.remote

import android.util.Log
import com.dicoding.githubapp.core.data.source.remote.network.ApiResponse
import com.dicoding.githubapp.core.data.source.remote.network.MainApiService
import com.dicoding.githubapp.core.data.source.remote.response.FollowItem
import com.dicoding.githubapp.core.data.source.remote.response.UserItem
import com.dicoding.githubapp.core.utils.getErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun searchUser(query: String): Flow<ApiResponse<List<UserItem>>> = flow {
        try {
            val response = mainApiService.searchUser(query)
            val users = response.items
            if (users != null) {
                emit(ApiResponse.Success(users))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> {
                            e.getErrorMessage().toString()
                            Log.e(
                                "RemoteDataSource::searchUser(HttpExceptionCode)",
                                e.getErrorMessage().toString()
                            )
                        }
                    }
                    emit(ApiResponse.Error(message.toString()))
                }

                is UnknownHostException -> {
                    emit(ApiResponse.Error("No internet connection"))
                    Log.e(
                        "RemoteDataSource::searchUser(UnknownHostException)",
                        "No internet connection"
                    )
                }

                else -> {
                    emit(ApiResponse.Error(e.message.toString()))
                    Log.e("RemoteDataSource::searchUser(Exception)", e.message.toString())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String): Flow<ApiResponse<UserItem>> = flow {
        try {
            val response = mainApiService.getDetailUser(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> {
                            e.getErrorMessage().toString()
                            Log.e(
                                "RemoteDataSource::getDetailUser(HttpExceptionCode)",
                                e.getErrorMessage().toString()
                            )
                        }
                    }
                    emit(ApiResponse.Error(message.toString()))
                    Log.e("RemoteDataSource::getDetailUser(HttpException)", message.toString())
                }

                is UnknownHostException -> {
                    emit(ApiResponse.Error("No internet connection"))
                    Log.e(
                        "RemoteDataSource::getDetailUser(UnknownHostException)",
                        "No internet connection"
                    )
                }

                else -> {
                    emit(ApiResponse.Error(e.message.toString()))
                    Log.e("RemoteDataSource::getDetailUser(Exception)", e.message.toString())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<FollowItem>>> = flow {
        try {
            val response = mainApiService.getUserFollowers(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> {
                            e.getErrorMessage().toString()
                            Log.e(
                                "RemoteDataSource::getUserFollowers(HttpExceptionCode)",
                                e.getErrorMessage().toString()
                            )
                        }
                    }
                    emit(ApiResponse.Error(message.toString()))
                }

                is UnknownHostException -> {
                    emit(ApiResponse.Error("No internet connection"))
                    Log.e(
                        "RemoteDataSource::getUserFollowers(UnknownHostException)",
                        "No internet connection"
                    )
                }

                else -> {
                    emit(ApiResponse.Error(e.message.toString()))
                    Log.e("RemoteDataSource::getUserFollowers(Exception)", e.message.toString())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<FollowItem>>> = flow {
        try {
            val response = mainApiService.getUserFollowing(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> {
                            e.getErrorMessage().toString()
                            Log.e(
                                "RemoteDataSource::getUserFollowing(HttpExceptionCode)",
                                e.getErrorMessage().toString()
                            )
                        }
                    }
                    emit(ApiResponse.Error(message.toString()))
                }

                is UnknownHostException -> {
                    emit(ApiResponse.Error("No internet connection"))
                    Log.e(
                        "RemoteDataSource::getUserFollowing(UnknownHostException)",
                        "No internet connection"
                    )
                }

                else -> {
                    emit(ApiResponse.Error(e.message.toString()))
                    Log.e("RemoteDataSource::getUserFollowing(Exception)", e.message.toString())
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}