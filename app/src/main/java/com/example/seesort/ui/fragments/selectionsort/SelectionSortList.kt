package com.example.seesort.ui.fragments.selectionsort

import java.util.UUID

data class SelectionSortList(
    val list: List<SelectionSortItem>
) {
    fun toDataList(): MutableList<Int> {
        return list.map {
            it.value
        }.toMutableList()
    }
}

data class SelectionSortItem(
    val id: String = UUID.randomUUID().toString(),
    val isComparing: Boolean,
    val isCurrentMinIndex: Boolean,
    val value: Int,
    val isSorted: Boolean = false
)