package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import androidx.compose.foundation.Image
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.theme.NoobleGreen

@Composable
fun ProfileClassClickable(
    className: String,
    onClassClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
            .clickable { onClassClick(className) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = className,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}