package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.CircularImage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import androidx.compose.foundation.clickable

// TODO: modifier activity post pour qu'il prenne un profil (du model de l'api) pour qu'il soit cliquable
// TODO: ou sinon qu'il affiche une image de profil par défaut avec les initiales

@Composable
fun ActivityPost(
    title: String,
    date: Instant,
    imageName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val datetime = date.toLocalDateTime(TimeZone.UTC)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .clickable { onClick()}
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${datetime.year}/${datetime.month.number}/${datetime.dayOfMonth}, ${datetime.hour}:${datetime.minute}",
                color = Color.Gray,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        CircularImage(
            imageRes = when(imageName)
            {
                "account" -> R.drawable.profile
                "class" -> R.drawable.book
                "role" -> R.drawable.profile
                else -> R.drawable.bell
            },
            size = 80,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostPreview() {
    SY43ProjectTheme {
        ActivityPost("Blabla", Clock.System.now(), "account")
    }
}