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
import e2su.nooble.models.BadgeModel
import e2su.utbm.sy43project.R
import java.nio.file.Files.size

@Composable
fun BadgeRender(badgeModel: BadgeModel) {
    Image(
        painter = painterResource(id = badgeModel.badgeId),
        contentDescription = "Badge ${badgeModel.name}",
        modifier = Modifier
            .size(8.dp) // Set the size of the image
            .clip(CircleShape) // Apply a circular shape
    )
}
