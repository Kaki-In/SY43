package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.CircularImage
import e2su.nooble.models.ProfileModel
import androidx.compose.foundation.lazy.items
import e2su.utbm.sy43project.ui.components.ProfileClassClickable

@Composable
fun ProfileScreen(
    profile : ProfileModel,
    onClassClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
            items(profile.classes) { course ->
                ProfileClassClickable(
                    className = course.name,
                    onClassClick = onClassClick
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    ProfileScreen()
}*/