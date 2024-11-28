package com.example.seesort.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seesort.DEFAULT_DELAY
import com.example.seesort.DELAY_MAX_VALUE
import com.example.seesort.DELAY_MIN_VALUE
import com.example.seesort.LIST_SIZE_MAX
import com.example.seesort.LIST_SIZE_MIN
import com.example.seesort.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomButtons(
    startSorting: () -> Unit,
    randomList: () -> Unit,
    sliderChange: (Int) -> Unit,
    speedSliderChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    listSizeInit: Int,
    isSorting: Boolean
) {
    var sizeSliderValue by remember { mutableFloatStateOf(listSizeInit.toFloat()) }
    var speedSliderValue by rememberSaveable { mutableFloatStateOf(DEFAULT_DELAY.toFloat()) }
    Column(
        modifier = modifier.padding(15.dp)
    ) {
        //size slider
        ElevatedCard(modifier = modifier.padding(0.dp, 10.dp)) {
            Row(
                modifier = modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.list_size),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    modifier = Modifier.weight(1f),
                    value = sizeSliderValue,
                    valueRange = LIST_SIZE_MIN.toFloat()..LIST_SIZE_MAX.toFloat(),
                    enabled = !isSorting,
                    thumb = {
                        Box(modifier = Modifier.size(24.dp)
                        ) {
                            if (sizeSliderValue < LIST_SIZE_MAX * 0.75) {
                                happyIcon()
                            } else {
                                shockedIcon()
                            }
                        }
                    },
                    colors = if (listSizeInit < LIST_SIZE_MAX * 0.75) {
                        SliderDefaults.colors(
                            thumbColor = Color(0xFFc8e6c9),
                            activeTrackColor = Color(0xFFc8e6c9),
                            inactiveTrackColor = Color.LightGray
                        )
                    } else {
                        SliderDefaults.colors(
                            thumbColor = Color(0xFFe57373),
                            activeTrackColor = Color(0xFFe57373),
                            inactiveTrackColor = Color.LightGray
                        )
                    },
                    onValueChange = {
                        val newValue = it.toInt()
                        if (newValue != sizeSliderValue.toInt()) {
                            sizeSliderValue = newValue.toFloat()
                            sliderChange(sizeSliderValue.toInt())
                        }
                    }
                )
            }
            //speed slider
            Row(
                modifier = modifier.padding(10.dp, 0.dp, 10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.speed),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    modifier = Modifier.weight(1f),
                    value = speedSliderValue,
                    valueRange = DELAY_MIN_VALUE.toFloat()..DELAY_MAX_VALUE.toFloat(),
                    enabled = !isSorting,
                    thumb = {
                        Box(modifier = Modifier.size(24.dp).offset(y = (-3).dp)
                        ) {
                            if (speedSliderValue < DELAY_MAX_VALUE * 0.85) {
                                turtleIcon()
                            } else {
                                rabbitIcon()
                            }
                        }
                    },
                    colors = if (speedSliderValue < DELAY_MAX_VALUE * 0.85) {
                        SliderDefaults.colors(
                            thumbColor = Color(0xFFc8e6c9),
                            activeTrackColor = Color(0xFFc8e6c9),
                            inactiveTrackColor = Color.LightGray
                        )
                    } else {
                        SliderDefaults.colors(
                            thumbColor = Color(0xFFe57373),
                            activeTrackColor = Color(0xFFe57373),
                            inactiveTrackColor = Color.LightGray
                        )
                    },
                    onValueChange = {
                        val newValue = it.toInt()
                        if (newValue != speedSliderValue.toInt()) {
                            speedSliderValue = newValue.toFloat()
                            speedSliderChange(speedSliderValue.toLong())
                        }
                    }
                )
            }
        }
        Row {
            // random button
            CustomIconButton(
                modifier = Modifier.weight(1f),
                onClick = { randomList() },
                enabled = !isSorting,
                iconResource = R.drawable.ic_random_dice,
                iconDescriptionResource = R.string.sort_icon
            )
            Spacer(modifier = Modifier.weight(1f))
            // sort button
            CustomIconButton(
                modifier = Modifier.weight(1f),
                onClick = { startSorting() },
                enabled = !isSorting,
                iconResource = R.drawable.ic_sort,
                iconDescriptionResource = R.string.sort_icon,
            )
        }
    }
}

@Composable
fun rabbitIcon() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.ic_rabbit),
        contentDescription = "rabbit icon",
    )
}

@Composable
fun turtleIcon() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.ic_turtle),
        contentDescription = "turtle icon"
    )
}
@Composable
fun happyIcon() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.ic_happy),
        contentDescription = "happy icon",
    )
}
@Composable
fun shockedIcon() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.ic_angry),
        contentDescription = "rabbit icon"
    )
}