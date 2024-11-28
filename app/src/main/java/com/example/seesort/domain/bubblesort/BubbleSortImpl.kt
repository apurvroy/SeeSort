package com.example.seesort.domain.bubblesort

import com.example.seesort.DELAY_MAX_VALUE
import com.example.seesort.swap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class BubbleSortImpl @Inject constructor() : BubbleSort {
    override fun runBubbleSort(list: MutableList<Int>, speed: Long): Flow<BubbleSortDomainModel> =
        flow {
            val size = list.size
            for (i in 0 until size) {
//                var isSwap = false
                for (j in 0 until size - i - 1) {
                    if (list[j] > list[j + 1]) {
                        list.swap(j, j + 1)
                        emit(BubbleSortDomainModel(j, true))
//                        isSwap = true
                    } else {
                        emit(BubbleSortDomainModel(j, false))
                    }
                    delay(DELAY_MAX_VALUE - speed)
                }
                emit(BubbleSortDomainModel(size-i-1,false, isSorted = true))
//                if (!isSwap) {
//                    break
//                }
            }

        }

}