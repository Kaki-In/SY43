package e2su.utbm.sy43project.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import e2su.nooble.models.ShopItemModel
import e2su.utbm.sy43project.ui.theme.NoobleGreen
import e2su.nooble.models.ShopItemType
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign


@Composable
fun ShopItem(
    item: ShopItemModel,
    onBuyClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onItemClick)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Image de l'item
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                when (item.itemType) {
                    ShopItemType.BADGE -> BadgePreview(item)
                    ShopItemType.PROFILE_BORDER -> StaticBorderPreview(item.image ?: R.drawable.profile)
                }
            }

            Text(
                text = "${item.price.toInt()} pièces",
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            if (!item.isEligible) {
                Text(
                    text = "Déblocage requis",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ShopItemPreviewDialog(
    item: ShopItemModel,
    onBuyClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(modifier = Modifier.size(120.dp)) {
                    when (item.itemType) {
                        ShopItemType.BADGE -> BadgePreview(item)
                        ShopItemType.PROFILE_BORDER -> StaticBorderPreview(item.image ?: R.drawable.profile)
                    }
                }

                Text(
                    text = "${item.price.toInt()} pièces",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                if (!item.isOwned) {
                    Button(
                        onClick = {
                            onBuyClick()
                            onDismiss()
                        },
                        enabled = item.isEligible,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NoobleGreen,
                            disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    ) {
                        Text("Acheter")
                    }
                } else {
                    Text("Déjà possédé", color = Color.Gray)
                }
            }
        }
    }
}