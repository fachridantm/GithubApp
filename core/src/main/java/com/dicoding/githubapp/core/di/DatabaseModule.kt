package com.dicoding.githubapp.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.githubapp.core.BuildConfig.PASSPHRASE
import com.dicoding.githubapp.core.data.source.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
}