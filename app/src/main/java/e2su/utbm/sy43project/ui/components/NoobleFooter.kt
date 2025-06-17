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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavRoutes

@Composable
fun NoobleFooter(
    onOpenHome: () -> Unit,
    onOpenClasses: () -> Unit,
    onOpenThread: () -> Unit,
    onOpenShop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
    )
    {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton (
                onOpenHome,
                modifier = Modifier.padding(3.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.home),
                    contentDescription = "Home Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )
            }
            IconButton (
                onOpenClasses,
                modifier = Modifier.padding(3.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.book),
                    contentDescription = "Classes Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )

            }
            IconButton (
                onOpenThread,
                modifier = Modifier.padding(3.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.bell),
                    contentDescription = "Thread Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )

            }
            IconButton (
                onOpenShop,
                modifier = Modifier.padding(3.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.cart),
                    contentDescription = "Shop Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )

            }
        }
    }
}