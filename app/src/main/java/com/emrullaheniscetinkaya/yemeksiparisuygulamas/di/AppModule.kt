package com.emrullaheniscetinkaya.yemeksiparisuygulamas.di

import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.datasource.YemekDatasource
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.repo.YemeklerRepository
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.database.retrofit.ApiUtils
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.database.retrofit.YemeklerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideYemeklerRepository(yemekDatasource: YemekDatasource): YemeklerRepository{
        return YemeklerRepository(yemekDatasource)
    }
    @Provides
    @Singleton
    fun provideYemekDatasource(yemeklerDao: YemeklerDao): YemekDatasource{
        return YemekDatasource(yemeklerDao)
    }

    @Provides
    @Singleton
    fun provideYemekDao(): YemeklerDao{
        return ApiUtils.getYemeklerDao()
    }

}