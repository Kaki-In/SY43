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
import androidx.navigation.NavController
import e2su.utbm.sy43project.R

// TODO : Fix icon sizes

@Composable
fun ActivityPost(
    title: String,
    date: String,
    user: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ){
        Column(
            modifier = Modifier
                .height(100.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = date,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }
        Image(
            painter = painterResource(user),
            contentDescription = "User Icon",
            modifier = Modifier
                .size(40.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostPreview() {
    SY43ProjectTheme {
        ActivityPost( "Blabla", "12/12/2023", R.drawable.book)
    }
}