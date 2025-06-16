package e2su.utbm.sy43project.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import e2su.utbm.sy43project.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavRoutes

@Composable
fun NoobleFooter(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))
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
                    .clickable { }//navController.navigate(AdminNavRoutes.CLASS_SELECT.route) }
            )
            Image(
                painter = painterResource(R.drawable.bell),
                contentDescription = "Activites Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable { }//navController.navigate(AdminNavRoutes.ACTIVITY.route) }
            )
            Image(
                painter = painterResource(R.drawable.cart),
                contentDescription = "Shop Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable { }//navController.navigate("shop") }
            )
        }
        HomeButton()//navController)
    }
}