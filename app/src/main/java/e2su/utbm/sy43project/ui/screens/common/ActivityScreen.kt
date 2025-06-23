package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.ui.views.ActivityPost
import kotlinx.datetime.Clock

@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier
) {
    Column(){
        Text(
            text = "Activities",
            modifier = modifier.padding(16.dp)
        )
        Spacer(modifier = modifier.height(16.dp))
        ActivityPost("Blabla", Clock.System.now(), "account")
        ActivityPost("blublu", Clock.System.now(), "account")
        ActivityPost("zeifu", Clock.System.now(), "role")
        ActivityPost("Blabla", Clock.System.now(), "role")
        ActivityPost("blublu", Clock.System.now(), "class")
        ActivityPost("zeifu", Clock.System.now(), "class")
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityPreview() {
    ActivityScreen()
}