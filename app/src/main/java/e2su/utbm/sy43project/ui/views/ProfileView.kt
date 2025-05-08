package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.CircularImage
import e2su.utbm.sy43project.ui.components.NoobleFooter
import e2su.utbm.sy43project.ui.components.NoobleHeader
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import e2su.utbm.sy43project.ui.components.HomeButton
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme

@Composable
fun ProfileView(courses: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp),
    ) {
        Row {
            CircularImage(imageRes = R.drawable.woof, size = 80)
            Column {
                Text(
                    text = "FRANCOIS",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "DUPONT",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cours suivis :",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(courses) { course ->
                Text(
                    text = course,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    val courses = listOf("Math", "Physics", "Chemistry", "Biology")
    ProfileView(courses = courses)
}