package e2su.utbm.sy43project.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import e2su.utbm.sy43project.R

@Composable
fun ShopItem(
    item: ShopItemModel,
    onBuyClick: () -> Unit,
    onPreviewClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Contenu commun
    val commonContent = @Composable {
        Row {
            // Image et informations de base
            when (item.itemType) {
                ShopItemType.BADGE -> BadgePreview(item)
                ShopItemType.PROFILE_BORDER -> StaticBorderPreview(item.image ?: R.drawable.profile)
            }

            Column {
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Text(text = "${item.price.toInt()} pièces", color = Color.Gray)
                // Messages conditionnels
                if (!item.isEligible) {
                    Text(
                        text = "Déblocage requis",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    // Boutons d'action
    val actionButton = @Composable {
        Row {
            if (item.itemType == ShopItemType.PROFILE_BORDER) {
                IconButton(onClick = onPreviewClick) {
                    Icon(Icons.Default.Visibility, "Aperçu")
                }
            }

            if (!item.isOwned) {
                Button(
                    onClick = onBuyClick,
                    enabled = item.isEligible,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NoobleGreen,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                    )
                ) {
                    Text("Acheter")
                }
            } else {
                Text("Possédé", color = Color.Gray, modifier = Modifier.padding(8.dp))
            }
        }
    }

    // Mise en page finale
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            commonContent()
            actionButton()
        }
    }
}