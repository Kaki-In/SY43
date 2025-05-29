package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.R
import e2su.nooble.models.ShopItemModel

@Composable
fun BadgePreview(item: ShopItemModel, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = item.image ?: R.drawable.profile),
        contentDescription = item.name,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .size(60.dp)
            .padding(4.dp)
    )
}