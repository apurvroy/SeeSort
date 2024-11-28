package com.example.seesort.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seesort.R

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
    enabled: Boolean = true,
    iconResource: Int,
    iconDescriptionResource: Int,
    containerColor: Color = Color(0xFFfbe9e7),
    contentColor: Color = Color(0xFFbcaaa4),
    disabledContainerColor: Color = Color(0xFFe0e0e0),
    disabledContentColor: Color = Color.White
) {
    ElevatedButton(
        modifier = modifier.height(50.dp),
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = iconResource),
                    contentDescription = stringResource(id = iconDescriptionResource),
                )
            }
            if (text.isNotBlank()) {
                Spacer(Modifier.width(3.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                )
            }
        }

    }
}

@Preview
@Composable
fun CustomIconButtonPreview() {
    MaterialTheme {
        CustomIconButton(
            modifier = Modifier,
            text = "",
            onClick = { },
            enabled = false,
            iconResource = R.drawable.ic_random,
            iconDescriptionResource = R.string.sort_icon,
        )
    }
}