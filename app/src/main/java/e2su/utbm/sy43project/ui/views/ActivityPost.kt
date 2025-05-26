package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.Alignment
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.ProfilePic
import e2su.utbm.sy43project.data.SampleData

// TODO : Fix icon sizes

@Composable
fun ActivityPost(
    title: String,
    date: String,
    profile: ProfileModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.LightGray)
            .border(1.dp, Color.Red)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Centre verticalement les éléments
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Prend tout l'espace disponible à gauche
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = date,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ProfilePic(
            profile,
            size = 80,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostPreview() {
    SY43ProjectTheme {
        ActivityPost( "Blabla", "12/12/2023", profile = SampleData.sampleProfile)
    }
}