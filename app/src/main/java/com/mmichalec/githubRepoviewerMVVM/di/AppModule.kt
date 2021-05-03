package com.mmichalec.githubRepoviewerMVVM.di

import android.app.Application
import androidx.room.Room
import com.mmichalec.githubRepoviewerMVVM.api.RepositoriesApi
import com.mmichalec.githubRepoviewerMVVM.data.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(RepositoriesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRepositoriesApi(retrofit: Retrofit): RepositoriesApi =
        retrofit.create(RepositoriesApi::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app:Application) : RepoDatabase =
        Room.databaseBuilder(app,RepoDatabase::class.java, "repo_database").build()

}