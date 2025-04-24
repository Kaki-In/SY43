package e2su.utbm.sy43project.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.ActivityPost

@Composable
fun ActivityView(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    ActivityPost("Blabla", "22/12/2023", R.drawable.book)
}

/*
@Preview(showBackground = true)
@Composable
fun ActivityViewPreview() {
    ActivityView(NavController)
}*/