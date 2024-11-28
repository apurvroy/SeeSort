package com.example.seesort.ui.fragments.bubblesort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seesort.DEFAULT_DELAY
import com.example.seesort.DEFAULT_SORTING_LIST_SIZE
import com.example.seesort.DELAY_MAX_VALUE
import com.example.seesort.ITEM_RANGE_END
import com.example.seesort.ITEM_RANGE_START
import com.example.seesort.domain.bubblesort.BubbleSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BubbleSortViewModel @Inject constructor(
    private val bubbleSort: BubbleSort
) : ViewModel() {
    private var _isSorting = MutableStateFlow(false)
    val isSorting: StateFlow<Boolean> = _isSorting

    private var _listToSort = MutableStateFlow(getRandomList())
    val listToSort: StateFlow<BubbleSortList> = _listToSort

    private var speed = DEFAULT_DELAY

    fun randomizeCurrentList(size: Int = listToSort.value.list.size) {
        _listToSort.value = getRandomList(size)
    }

    fun speedSliderChange(speed: Long) {
        this.speed = speed
    }

    private fun getRandomList(size: Int = DEFAULT_SORTING_LIST_SIZE): BubbleSortList {
        val list = (0 until size).map {
            BubbleSortItem(isComparing = false, value = (ITEM_RANGE_START..ITEM_RANGE_END).random())
        }
        return BubbleSortList(list)
    }

    fun startSorting() {
        val dataList = _listToSort.value.toDataList()
        _isSorting.value = true
        viewModelScope.launch {
            bubbleSort.runBubbleSort(dataList, speed).collect { bubbleSortInfo ->
                val index = bubbleSortInfo.currentItem

                if (bubbleSortInfo.isSorted) {
                    val newListSorted = _listToSort.value.list.toMutableList()
                    newListSorted[index] = newListSorted[index].copy(isSorted = true)
                    _listToSort.value = _listToSort.value.copy(list = newListSorted)
                    return@collect
                }
                // mark current items as being compared
                val newListCompare = _listToSort.value.list.toMutableList()
                newListCompare[index] = newListCompare[index].copy(isComparing = true)
                if (index < newListCompare.size - 1) newListCompare[index + 1] =
                    newListCompare[index + 1].copy(isComparing = true)
                _listToSort.value = _listToSort.value.copy(list = newListCompare)
                delay(DELAY_MAX_VALUE - speed)

                // swap if necessary
                if (bubbleSortInfo.didSwap) {
                    val newListSwap = _listToSort.value.list.toMutableList()
                    val temp = newListSwap[index].copy()
                    newListSwap[index] = newListSwap[index + 1].copy()
                    newListSwap[index + 1] = temp
                    _listToSort.value = _listToSort.value.copy(list = newListSwap)
                }

                delay(DELAY_MAX_VALUE - speed)
                // uncheck after comparing
                val newList = _listToSort.value.list.toMutableList()
                newList[index] = newList[index].copy(isComparing = false)
                newList[index + 1] = newList[index + 1].copy(isComparing = false)
                _listToSort.value = _listToSort.value.copy(list = newList)
            }
            _isSorting.value = false
        }
    }
}