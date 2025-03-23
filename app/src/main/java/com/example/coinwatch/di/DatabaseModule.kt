package com.example.coinwatch.di

import android.content.Context
import androidx.room.Room
import com.example.coinwatch.data.local.CoinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): CoinDatabase {
        return Room.databaseBuilder(
            context,
            CoinDatabase::class.java,
            "coin_watch_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoinDao(database: CoinDatabase) = database.coinDao()
}
