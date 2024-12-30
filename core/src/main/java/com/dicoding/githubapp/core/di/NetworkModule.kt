package com.dicoding.githubapp.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.dicoding.githubapp.core.BuildConfig.BASE_URL
import com.dicoding.githubapp.core.BuildConfig.CERTIFICATE_PINNER_1
import com.dicoding.githubapp.core.BuildConfig.CERTIFICATE_PINNER_2
import com.dicoding.githubapp.core.BuildConfig.DEBUG
import com.dicoding.githubapp.core.BuildConfig.GITHUB_TOKEN
import com.dicoding.githubapp.core.BuildConfig.HOSTNAME
import com.dicoding.githubapp.core.data.source.remote.network.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOSTNAME, SHA_256 + CERTIFICATE_PINNER_1)
            .add(HOSTNAME, SHA_256 + CERTIFICATE_PINNER_2)
            .build()
        return OkHttpClient.Builder()
            .apply {
                if (DEBUG) {
                    addInterceptor(chuckerInterceptor)
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                } else {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
                }
            }
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("Authorization","token $GITHUB_TOKEN")
                    .build()
                    .let(chain::proceed)
            }
            .retryOnConnectionFailure(true)
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector {
        return ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    }

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .createShortcut(true)
            .build()
    }

    @Provides
    fun provideMainApiService(client: OkHttpClient): MainApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MainApiService::class.java)
    }

    companion object {
        private const val SHA_256 = "sha256/"
    }
}
