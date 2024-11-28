package com.example.seesort

import com.example.seesort.domain.bubblesort.BubbleSort
import com.example.seesort.domain.bubblesort.BubbleSortImpl
import com.example.seesort.domain.selectionsort.SelectionSort
import com.example.seesort.domain.selectionsort.SelectionSortImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providesBubbleSort(): BubbleSort = BubbleSortImpl()

    @Singleton
    @Provides
    fun providesSelectionSort(): SelectionSort = SelectionSortImpl()
}