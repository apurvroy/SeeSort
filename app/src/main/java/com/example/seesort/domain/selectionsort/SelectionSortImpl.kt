package com.example.seesort.domain.selectionsort

import com.example.seesort.DELAY_MAX_VALUE
import com.example.seesort.swap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectionSortImpl @Inject constructor() : SelectionSort {
    override fun runSelectionSort(
        list: MutableList<Int>,
        speed: Long
    ): Flow<SelectionSortDomainModel> = flow {
        val size = list.size
        for (i in 0 until size ) {
            var minIndex = i
            emit(SelectionSortDomainModel(i, minIndex, -1, false))
            for (j in i + 1 until size) {
                emit(SelectionSortDomainModel(i, minIndex, j, false))
                if (list[j] < list[minIndex]) {
                    minIndex = j
                    emit(SelectionSortDomainModel(i, minIndex, j, false))
                }
            }
            if (minIndex != i) {
                list.swap(i, minIndex)
                emit(SelectionSortDomainModel(i, minIndex, minIndex, true))
                delay(DELAY_MAX_VALUE - speed)
            } else {
                emit(SelectionSortDomainModel(i, minIndex, minIndex, false))
            }
            emit(SelectionSortDomainModel(i, minIndex, minIndex, false, isSorted = true))
        }
    }
}