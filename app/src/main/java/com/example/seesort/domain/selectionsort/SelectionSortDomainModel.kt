package com.example.seesort.domain.selectionsort

data class SelectionSortDomainModel(
    val outerLoopCurrentIndex: Int,
    val currentMinIndex: Int,
    val innerLoopCurrentIndex: Int,
    val didSwap: Boolean,
    val isSorted: Boolean = false
)
