package e2su.utbm.sy43project.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import e2su.utbm.sy43project.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import e2su.utbm.sy43project.ui.theme.NoobleGreen
import e2su.utbm.sy43project.ui.views.LoginBlock

// TODO : Fix icon sizes

@Composable
fun NoobleFooter(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize() // Remplit tout l'espace disponible
            .background(Color.White), // Fond blanc pour éviter les chevauchements
        verticalArrangement = Arrangement.SpaceBetween // Place le footer en bas
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Espace flexible pour pousser le footer en bas
        Row(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(3.dp, Color.Black),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.book),
                contentDescription = "Classes Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
            Image(
                painter = painterResource(R.drawable.bell),
                contentDescription = "Activites Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
            Image(
                painter = painterResource(R.drawable.cart),
                contentDescription = "Additional Content Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
        }
        HomeButton(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun FooterPreview() {
    SY43ProjectTheme {
        val fakeNavController = androidx.navigation.testing.TestNavHostController(LocalContext.current)
        NoobleFooter(fakeNavController)
    }
}