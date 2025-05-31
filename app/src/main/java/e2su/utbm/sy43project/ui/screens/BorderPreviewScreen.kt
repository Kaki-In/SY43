package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import e2su.nooble.models.ProfileBorderData
import e2su.nooble.models.ShopItemModel
import e2su.utbm.sy43project.ui.components.border.BorderPreview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size

@Composable
fun BorderPreviewScreen(availableBorders: List<Pair<ShopItemModel, ProfileBorderData>>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Prévisualisation du profil avec la bordure sélectionnée
        // Code pour sélectionner et afficher une bordure active

        // Liste des bordures disponibles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            availableBorders.forEach { (item, border) ->
                BorderPreview(
                    item = item,
                    borderData = border,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { /* Sélectionner cette bordure */ }
                )
            }
        }
    }
}