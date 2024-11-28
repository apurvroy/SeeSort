package com.example.seesort.ui.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedIcon(
    @RawRes animationResId: Int,
    modifier: Modifier = Modifier
) {
    val compositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))

    if (compositionResult.isSuccess) {
        val composition = compositionResult.value

        val animationState = animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )

        LottieAnimation(
            composition = composition,
            progress = animationState.progress,
            modifier = modifier
        )
    }
}