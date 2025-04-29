package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.ActivityPost
import e2su.utbm.sy43project.ui.components.NoobleFooter
import e2su.utbm.sy43project.ui.components.NoobleHeader
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.ui.components.ClassButton

@Composable
fun ClassSelectView(
    navController: NavHostController,
    courses: List<String>, // Liste des cours
    modifier: Modifier = Modifier
) {
    Column {
        NoobleHeader()
        Text(text = "Cours suivis", modifier = modifier.padding(16.dp))
        //Spacer(modifier = modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Deux colonnes
            modifier = modifier.padding(16.dp) ,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), // Espacement horizontal
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp) // Espacement vertical
        ) {
            items(courses) { course ->
                ClassButton(
                    text = course,
                    navController = navController
                )
            }
        }
        NoobleFooter()
    }
}


@Preview(showBackground = true)
@Composable
fun ClassSelectPreview() {
    val fakeNavController = androidx.navigation.testing.TestNavHostController(LocalContext.current)
    val courses = listOf("Math", "Physics", "Chemistry", "Biology")
    ClassSelectView(navController = fakeNavController, courses = courses)
}