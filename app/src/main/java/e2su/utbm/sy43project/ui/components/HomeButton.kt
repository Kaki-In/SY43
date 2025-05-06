package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import e2su.utbm.sy43project.R

@Composable
fun HomeButton(navController: NavController) {
    Icon(
        painter = painterResource(id = R.drawable.home), // Assurez-vous d'avoir une icône "ic_home" dans vos ressources
        contentDescription = "Retour à la page de login",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(24.dp) // Taille de l'icône
            .clickable { navController.navigate("login") } // Action au clic
    )
}