package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.CircularImage
import e2su.utbm.sy43project.ui.components.ProfileClassClickable
import e2su.nooble.models.ProfileModel
import e2su.nooble.models.ProfileWithBadgesAndBorders
import e2su.nooble.models.BadgeShopItem

@Composable
fun ProfileScreen(
    profileWithBadgesAndBorders: ProfileWithBadgesAndBorders,
    onClassClick: (String) -> Unit,
    onPPClick: () -> Unit = {},
    modifier : Modifier = Modifier
) {
    var selectedBadge by remember { mutableStateOf<BadgeShopItem?>(null) }

    val profile = profileWithBadgesAndBorders.profile
    val badges = profileWithBadgesAndBorders.badges

    Column(
        modifier = modifier
            .padding(16.dp),
    ) {
        Row {
            CircularImage(imageRes = profile.image, size = 80)
            Column {
                Text(
                    text = profile.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = profile.surname,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (badges.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                items(badges) { badge ->
                    BadgeItem(badge) { selectedBadge = badge }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cours suivis :",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(profile.classes) { course ->
                ProfileClassClickable(
                    className = course.name,
                    onClassClick = onClassClick
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (selectedBadge != null) {
            AlertDialog(
                onDismissRequest = { selectedBadge = null },
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedBadge!!.shopItem.name, style = MaterialTheme.typography.titleLarge)
                        IconButton(onClick = { selectedBadge = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Fermer")
                        }
                    }
                },
                text = { Text(selectedBadge!!.description) },
                confirmButton = {}
            )
        }
    }
}

@Composable
fun BadgeItem(badge: BadgeShopItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Text(text = badge.shopItem.name, fontWeight = FontWeight.Medium)
    }
}