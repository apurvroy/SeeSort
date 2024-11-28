package com.example.seesort.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seesort.R

@Composable
fun Header(
    headerText: String,
    animResId: Int,
    isCancelAvailable: Boolean = false,
    onCancelCLicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Ensures the row is spaced between
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .wrapContentSize()
                ) {
                    AnimatedIcon(
                        animationResId = animResId, modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                }
                Text(
                    text = headerText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }
        }
        if (isCancelAvailable) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .wrapContentSize(),
            ) {
                IconButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = onCancelCLicked,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "Cancel icon",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewHeader() {
    MaterialTheme() {
        Header(
            headerText = stringResource(id = R.string.app_name),
            animResId = R.drawable.ic_turtle,
            isCancelAvailable = true
        )
    }
}