package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import e2su.nooble.models.ActivityModel
import e2su.utbm.sy43project.ui.views.ActivityPost
import kotlin.toString

@Composable
fun ActivityScreen(activities: List<ActivityModel>) {
    var selectedActivity by remember { mutableStateOf<ActivityModel?>(null) }
    val newPosts = activities.filter { !it.isRead }.sortedByDescending { it.date }
    val oldPosts = activities.filter { it.isRead }.sortedByDescending { it.date }

    LazyColumn {
        if (newPosts.isNotEmpty()) {
            item {
                Text(
                    "Nouveaux postes",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(newPosts) { activity ->
                ActivityPost(
                    title = activity.title,
                    date = activity.date.toString(),
                    profile = activity.sender,
                    onClick = { selectedActivity = activity }
                )
            }
        }
        if (oldPosts.isNotEmpty()) {
            item {
                Text(
                    "Anciens postes",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(oldPosts) { activity ->
                ActivityPost(
                    title = activity.title,
                    date = activity.date.toString(),
                    profile = activity.sender,
                    modifier = Modifier
                        .clickable { selectedActivity = activity }
                )
            }
        }
    }

    if (selectedActivity != null) {
        AlertDialog(
            onDismissRequest = { selectedActivity = null },
            title = { Text(selectedActivity!!.title) },
            text = { Text(selectedActivity!!.content) },
            confirmButton = {}
        )
    }
}