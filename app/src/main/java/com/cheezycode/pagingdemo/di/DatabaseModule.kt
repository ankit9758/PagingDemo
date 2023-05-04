package com.cheezycode.pagingdemo.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.cheezycode.pagingdemo.db.QuoteDatabase
import com.cheezycode.pagingdemo.network.QuotesAPI
import com.cheezycode.pagingdemo.paging.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : QuoteDatabase {
        return Room.databaseBuilder(context, QuoteDatabase::class.java, "quoteDB").build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideRepository(@ApplicationContext context: Context,quotesAPI:QuotesAPI,quoteDatabase:QuoteDatabase) : QuoteRepository {
        return QuoteRepository(context,  quotesAPI,quoteDatabase)
    }
}