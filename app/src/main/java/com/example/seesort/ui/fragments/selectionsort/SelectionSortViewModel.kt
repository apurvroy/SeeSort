package com.example.seesort.ui.fragments.selectionsort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seesort.DEFAULT_DELAY
import com.example.seesort.DELAY_MAX_VALUE
import com.example.seesort.ITEM_RANGE_END
import com.example.seesort.ITEM_RANGE_START
import com.example.seesort.domain.selectionsort.SelectionSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionSortViewModel @Inject constructor(
    private val selectionSort: SelectionSort
) : ViewModel() {
    private var _isSorting = MutableStateFlow(false)
    val isSorting: StateFlow<Boolean> = _isSorting

    private var _listToSort = MutableStateFlow(getRandomList())
    val listToSort: MutableStateFlow<SelectionSortList> = _listToSort

    private var speed = DEFAULT_DELAY

    fun randomizeCurrentList(size: Int = listToSort.value.list.size) {
        _listToSort.value = getRandomList(size)
    }

    fun speedSliderChange(speed: Long) {
        this.speed = speed
    }

    private fun getRandomList(size: Int = 7): SelectionSortList {
        val list = (0 until size).map {
            SelectionSortItem(
                isComparing = false,
                isCurrentMinIndex = false,
                value = (ITEM_RANGE_START..ITEM_RANGE_END).random()
            )
        }
        return SelectionSortList(list)
    }

    fun startSorting() {
        val dataList = listToSort.value.toDataList()
        _isSorting.value = true
        viewModelScope.launch {
            selectionSort.runSelectionSort(dataList, speed).collect { selectionSortInfo ->
                val currentOuterLoopIndex = selectionSortInfo.outerLoopCurrentIndex
                val currentMinIndex = selectionSortInfo.currentMinIndex
                val currentInnerLoopIndex = selectionSortInfo.innerLoopCurrentIndex

                if(selectionSortInfo.isSorted){
                    val newListSorted = _listToSort.value.list.toMutableList()
                    newListSorted[currentOuterLoopIndex] = newListSorted[currentOuterLoopIndex].copy(isSorted = true)
                    _listToSort.value = _listToSort.value.copy(list = newListSorted)
                    return@collect
                }
                if (currentInnerLoopIndex == -1) {
                    val newListCompare = _listToSort.value.list.toMutableList()
                    //mark min index
                    newListCompare[currentMinIndex] =
                        newListCompare[currentMinIndex].copy(isCurrentMinIndex = true)
                    _listToSort.value = _listToSort.value.copy(list = newListCompare)
                    delay(DELAY_MAX_VALUE - speed)

                    //remove the mark
                    newListCompare[currentMinIndex] =
                        newListCompare[currentMinIndex].copy(isCurrentMinIndex = false)
                    _listToSort.value = _listToSort.value.copy(list = newListCompare)
                    delay(DELAY_MAX_VALUE - speed)
                } else {
                    val newListCompare = _listToSort.value.list.toMutableList()
                    //mark current items
                    newListCompare[currentMinIndex] =
                        newListCompare[currentMinIndex].copy(isCurrentMinIndex = true)
                    newListCompare[currentInnerLoopIndex] =
                        newListCompare[currentInnerLoopIndex].copy(isComparing = true)
                    _listToSort.value = _listToSort.value.copy(list = newListCompare)
                    delay(DELAY_MAX_VALUE - speed)

                    //swap if possible
                    if (selectionSortInfo.didSwap) {
                        val newListSwap = _listToSort.value.list.toMutableList()
                        val temp = newListSwap[currentMinIndex].copy()
                        newListSwap[currentMinIndex] = newListSwap[currentOuterLoopIndex].copy()
                        newListSwap[currentOuterLoopIndex] = temp
                        _listToSort.value = _listToSort.value.copy(list = newListSwap)
                        delay(DELAY_MAX_VALUE - speed)
                    }

                    //remove the mark
                    val newList = _listToSort.value.list.toMutableList()
                    newList[currentMinIndex] = newList[currentMinIndex].copy(
                        isCurrentMinIndex = false,
                        isComparing = false
                    )
                    newList[currentOuterLoopIndex] = newList[currentOuterLoopIndex].copy(
                        isCurrentMinIndex = false,
                        isComparing = false
                    )
                    newList[currentInnerLoopIndex] = newList[currentInnerLoopIndex].copy(
                        isComparing = false,
                        isCurrentMinIndex = false
                    )
                    _listToSort.value = _listToSort.value.copy(list = newList)
                    delay(DELAY_MAX_VALUE - speed)
                }
            }
            _isSorting.value = false
        }
    }
}