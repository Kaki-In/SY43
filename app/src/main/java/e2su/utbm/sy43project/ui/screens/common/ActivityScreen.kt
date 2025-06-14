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
import e2su.utbm.sy43project.data.SampleData

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
        ActivityPost("Blabla", "22/12/2023", SampleData.sampleProfile)
        ActivityPost("blublu", "22/12/2023", SampleData.sampleProfile)
        ActivityPost("zeifu", "22/12/2023", SampleData.sampleProfile)
        ActivityPost("Blabla", "22/12/2023", SampleData.sampleProfile)
        ActivityPost("blublu", "22/12/2023", SampleData.sampleProfile)
        ActivityPost("zeifu", "22/12/2023", SampleData.sampleProfile)
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityPreview() {
    ActivityScreen()
}