package com.ta.ittpizen.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ta.ittpizen.ui.component.text.TextBodySmall
import com.ta.ittpizen.ui.theme.ITTPizenTheme
import com.ta.ittpizen.ui.theme.PrimaryRed

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    enable: Boolean = true,
    loading: Boolean = false
) {
    val buttonText = if (loading) "Loading..." else text
    val buttonEnabled = if (loading) false else enable
    val buttonBackground = if (buttonEnabled) PrimaryRed else Color(0xFFDEDEDE)
    val textButton = if (buttonEnabled) Color.White else Color(0xFFAAAAAA)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 100.dp))
            .clickable(buttonEnabled) { onClick() }
            .background(color = buttonBackground)
            .padding(vertical = 10.dp, horizontal = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        TextBodySmall(
            text = buttonText,
            color = textButton
        )
    }
}

@Preview
@Composable
fun PreviewPrimaryButton() {
    ITTPizenTheme {
        Surface {
            PrimaryButton(
                text = "Let’s Go!"
            )
        }
    }
}
