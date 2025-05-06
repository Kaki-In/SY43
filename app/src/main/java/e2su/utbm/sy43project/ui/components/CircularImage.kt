package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import e2su.utbm.sy43project.R

@Composable
fun CircularImage(imageRes: Int, size: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Circular Image",
        modifier = Modifier
            .size(size.dp) // Définit la taille de l'image
            .clip(CircleShape) // Applique une forme circulaire
    )
}

@Preview(showBackground = true)
@Composable
fun CircularImagePreview() {
    CircularImage(imageRes = R.drawable.book, size = 80)
}