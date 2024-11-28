package com.example.seesort.domain.selectionsort

import kotlinx.coroutines.flow.Flow

interface SelectionSort {
    fun runSelectionSort(list: MutableList<Int>, speed: Long): Flow<SelectionSortDomainModel>
}