package com.example.data.di

import com.example.data.repository.*
import com.example.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class Repositories {
    @Binds
    @Singleton
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository


    @Binds
    @Singleton
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl):
            HomeRepository

    @Binds
    @Singleton
    abstract fun provideBlocRepository(blocRepositoryImpl: BlocRepositoryImpl):
            BlocRepository


    @Binds
    @Singleton
    abstract fun provideFloorRepository(floorRepositoryImpl: FloorRepositoryImpl):
            FloorRepository


    @Binds
    @Singleton
    abstract fun provideUnitRepository(unitRepositoryImpl: UnitRepositoryImpl):
            UnitRepository

    @Binds
    @Singleton
    abstract fun providePostRepository(postRepositoryImpl: PostRepositoryImpl):
            PostRepository


}