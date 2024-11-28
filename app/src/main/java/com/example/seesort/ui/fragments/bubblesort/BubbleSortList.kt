package com.example.seesort.ui.fragments.bubblesort

import java.util.UUID

data class BubbleSortList(
    val list: List<BubbleSortItem>
) {
    fun toDataList(): MutableList<Int> {
        return list.map {
            it.value
        }.toMutableList()
    }
}

data class BubbleSortItem(
    val id: String = UUID.randomUUID().toString(),
    val isComparing: Boolean,
    val value: Int,
    val isSorted: Boolean = false
)
