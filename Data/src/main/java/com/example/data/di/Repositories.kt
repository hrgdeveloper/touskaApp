package com.example.data.di

import com.example.data.repository.BlocRepositoryImpl
import com.example.data.repository.HomeRepositoryImpl
import com.example.data.repository.LoginRepositoryImpl
import com.example.domain.repositories.BlocRepository
import com.example.domain.repositories.HomeRepository
import com.example.domain.repositories.LoginRepository
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
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl):LoginRepository


    @Binds
    @Singleton
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl):HomeRepository

    @Binds
    @Singleton
    abstract fun provideBlocRepository(blocRepositoryImpl: BlocRepositoryImpl):BlocRepository


}