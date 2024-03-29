package com.ta.ittpizen.ui.component.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ta.ittpizen.ui.R
import com.ta.ittpizen.ui.component.iconbutton.BaseIconButton
import com.ta.ittpizen.ui.component.text.TextTitleLarge
import com.ta.ittpizen.ui.theme.ITTPizenTheme

@ExperimentalMaterial3Api
@Composable
fun DetailTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigationClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseIconButton(
            icon = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            onClick = onNavigationClick
        )
        Spacer(modifier = Modifier.width(12.dp))
        TextTitleLarge(
            text = title,
            color = Color(0xFF333538),
            modifier = Modifier.offset(y = (-1).dp)
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewDetailTopAppBar() {
    ITTPizenTheme {
        Surface {
            DetailTopAppBar(
                title = "Register"
            )
        }
    }
}
