package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.testing.TestNavHostController
import e2su.utbm.sy43project.ui.components.ClassButton

@Composable
fun ClassSelectScreen(
    navController: NavHostController,
    courses: List<String>, // Liste des cours
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier ) {
        Text(text = "Cours suivis", modifier = Modifier.padding(16.dp))
        //Spacer(modifier = modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Deux colonnes
            modifier = modifier.padding(16.dp) ,
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacement horizontal
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacement vertical
        ) {
            items(courses) { course ->
                ClassButton(
                    text = course,
                    navController = navController
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ClassSelectPreview() {
    val fakeNavController = TestNavHostController(LocalContext.current)
    val courses = listOf("Math", "Physics", "Chemistry", "Biology")
    ClassSelectScreen(navController = fakeNavController, courses = courses)
}