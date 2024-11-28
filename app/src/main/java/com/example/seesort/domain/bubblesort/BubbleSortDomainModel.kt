package com.example.seesort.domain.bubblesort

data class BubbleSortDomainModel(
    val currentItem: Int,
    val didSwap: Boolean,
    val isSorted: Boolean = false
)