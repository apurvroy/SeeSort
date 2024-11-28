package com.example.seesort

import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieConstants


const val DEFAULT_DELAY = 600.toLong()
const val DEFAULT_SORTING_LIST_SIZE = 7
const val DELAY_MIN_VALUE = 300L
const val DELAY_MAX_VALUE = 1000L

const val ITEM_RANGE_START = 20
const val ITEM_RANGE_END = 80
const val LIST_SIZE_MIN = 2
const val LIST_SIZE_MAX = 20

const val IS_SORTING_FRAGMENT_ACTIVE = "isSortingFragmentActive"
fun <E> MutableList<E>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}