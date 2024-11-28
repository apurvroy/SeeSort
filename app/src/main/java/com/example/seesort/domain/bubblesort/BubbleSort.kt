package com.example.seesort.domain.bubblesort

import kotlinx.coroutines.flow.Flow

interface BubbleSort {
    fun runBubbleSort(list: MutableList<Int>, speed: Long): Flow<BubbleSortDomainModel>
}