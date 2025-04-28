package e2su.utbm.sy43project.ui.components

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
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.compose.ui.platform.LocalContext
import e2su.utbm.sy43project.R

// TODO : Fix icon sizes

@Composable
fun ClassButton(
    text: String,
    navController: NavHostController
) {
    Button(
        onClick = { navController.navigate(text) },
        modifier = Modifier
            .size(100.dp) // Taille carrée : 100x100 dp
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun ClassButtonPreview() {
    val fakeNavController = androidx.navigation.testing.TestNavHostController(LocalContext.current)
    ClassButton(text = "activity", navController = fakeNavController)
}