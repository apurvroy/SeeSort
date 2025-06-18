package com.example.seesort.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.seesort.R
import com.example.seesort.ui.theme.Primary
import com.example.seesort.ui.theme.SeeSortTheme

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
    enabled: Boolean = true,
    iconResource: Int,
    iconDescriptionResource: Int,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    disabledContainerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
) {
    ElevatedButton(
        modifier = modifier.height(56.dp),
        onClick = { onClick() },
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = stringResource(id = iconDescriptionResource),
                modifier = Modifier.size(24.dp),
                tint = if (enabled) contentColor else disabledContentColor
            )

            if (text.isNotBlank()) {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun CustomIconButtonPreview() {
    SeeSortTheme {
        CustomIconButton(
            modifier = Modifier,
            text = "Sort",
            onClick = { },
            enabled = true,
            iconResource = R.drawable.ic_sort,
            iconDescriptionResource = R.string.sort_icon,
        )
    }
}

@Preview
@Composable
fun DisabledCustomIconButtonPreview() {
    SeeSortTheme {
        CustomIconButton(
            modifier = Modifier,
            text = "",
            onClick = { },
            enabled = false,
            iconResource = R.drawable.ic_random_dice,
            iconDescriptionResource = R.string.sort_icon,
        )
    }
}