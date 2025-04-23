package e2su.utbm.sy43project.ui.components

import android.preference.PreferenceActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import androidx.compose.foundation.Image
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.theme.NoobleGreen
import e2su.utbm.sy43project.ui.views.LoginBlock

// TODO : Fix icon sizes

@Composable
fun NoobleHeader(
    //title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(NoobleGreen)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.book),
            contentDescription = "Menu Icon",
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
        )
        Text(
            text = "Nooble",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        Image(
            painter = painterResource(R.drawable.clock),
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    SY43ProjectTheme {
        NoobleHeader()
    }
}