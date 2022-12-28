package com.example.fitnesss.di

import com.example.fitnesss.models.library.LibraryItem
import com.example.fitnesss.models.library.LibraryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindLibraryRepository(impl: LibraryItem): LibraryRepository
}