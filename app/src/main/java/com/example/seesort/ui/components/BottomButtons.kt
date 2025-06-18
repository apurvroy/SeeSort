package com.example.seesort.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.seesort.DEFAULT_DELAY
import com.example.seesort.DELAY_MAX_VALUE
import com.example.seesort.DELAY_MIN_VALUE
import com.example.seesort.LIST_SIZE_MAX
import com.example.seesort.LIST_SIZE_MIN
import com.example.seesort.R
import com.example.seesort.ui.theme.sortedGreen
import com.example.seesort.ui.theme.swappingRed

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
        modifier = modifier.padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Size slider
                Text(
                    text = stringResource(id = R.string.list_size),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    value = sizeSliderValue,
                    valueRange = LIST_SIZE_MIN.toFloat()..LIST_SIZE_MAX.toFloat(),
                    enabled = !isSorting,
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            if (sizeSliderValue < LIST_SIZE_MAX * 0.75) {
                                happyIcon()
                            } else {
                                shockedIcon()
                            }
                        }
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = if (sizeSliderValue < LIST_SIZE_MAX * 0.75) sortedGreen.copy(alpha = 0.7f) else swappingRed.copy(alpha = 0.7f),
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    onValueChange = {
                        val newValue = it.toInt()
                        if (newValue != sizeSliderValue.toInt()) {
                            sizeSliderValue = newValue.toFloat()
                            sliderChange(sizeSliderValue.toInt())
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Speed slider
                Text(
                    text = stringResource(id = R.string.speed),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    value = speedSliderValue,
                    valueRange = DELAY_MIN_VALUE.toFloat()..DELAY_MAX_VALUE.toFloat(),
                    enabled = !isSorting,
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            if (speedSliderValue < DELAY_MAX_VALUE * 0.85) {
                                turtleIcon()
                            } else {
                                rabbitIcon()
                            }
                        }
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = if (speedSliderValue < DELAY_MAX_VALUE * 0.85) sortedGreen.copy(alpha = 0.7f) else swappingRed.copy(alpha = 0.7f),
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Random button
            CustomIconButton(
                modifier = Modifier.weight(1f),
                onClick = { randomList() },
                enabled = !isSorting,
                iconResource = R.drawable.ic_random_dice,
                iconDescriptionResource = R.string.sort_icon
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Sort button
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