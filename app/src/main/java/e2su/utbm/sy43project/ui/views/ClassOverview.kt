package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.ActivityPost
import e2su.utbm.sy43project.ui.components.HomeButton
import e2su.utbm.sy43project.ui.components.NoobleFooter
import e2su.utbm.sy43project.ui.components.NoobleHeader

@Composable
fun ClassOverview(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(){
        NoobleHeader()
        Text(
            text = "Titre UE",
            modifier = modifier.padding(16.dp)
        )
        Spacer(modifier = modifier.height(16.dp))
        //Créer composant pour les sections de l'UE ?
        HomeButton(navController)
        NoobleFooter()
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityViewPreview() {
    val fakeNavController = androidx.navigation.testing.TestNavHostController(LocalContext.current)
    ActivityView(navController = fakeNavController)
}