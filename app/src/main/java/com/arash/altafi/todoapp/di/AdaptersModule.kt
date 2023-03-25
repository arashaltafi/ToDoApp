package com.arash.altafi.todoapp.di

import com.arash.altafi.todoapp.ui.objectBox.adapter.RecyclerAdapterObjectBox
import com.arash.altafi.todoapp.ui.room.adapter.RecyclerAdapterRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdaptersModule {

    @FragmentScoped
    @Provides
    fun provideRecyclerAdapterObjectBox() = RecyclerAdapterObjectBox()

    @FragmentScoped
    @Provides
    fun provideRecyclerAdapterRoom() = RecyclerAdapterRoom()

}