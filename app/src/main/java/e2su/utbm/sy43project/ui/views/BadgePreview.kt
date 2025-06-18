package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel

@Composable
fun BadgePreview(
    reachable: Boolean,
    badgeModel: NoobleApiBadgeModel,
    maxBadgeLevel: Int,
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
            .padding(17.dp)
            .width(50.dp)
    ) {
        if (reachable)
        {
            Image(
                bitmap = bitmap,
                contentDescription = "badge icon",
                modifier = Modifier
                    .padding(5.dp)
                    .size(30.dp)
            )
        } else {
            val colorMatrix = ColorMatrix()
            colorMatrix.setToSaturation(0f)

            Image(
                bitmap = bitmap,
                contentDescription = "badge icon",
                modifier = Modifier
                    .padding(5.dp)
                    .size(30.dp)
                ,
                colorFilter = ColorFilter.colorMatrix(colorMatrix)
            )
        }

        Text(
            badgeModel.name,
            fontSize = 14.sp,
        )

        Text(
            "${badgeModel.level} of $maxBadgeLevel",
            fontSize = 10.sp,
            fontStyle = FontStyle.Italic
        )
    }
}
