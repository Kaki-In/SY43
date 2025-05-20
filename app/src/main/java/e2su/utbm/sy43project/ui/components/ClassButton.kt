package e2su.utbm.sy43project.ui.components

import android.R.attr.shape
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navController.navigate(text) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .aspectRatio(1f)
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