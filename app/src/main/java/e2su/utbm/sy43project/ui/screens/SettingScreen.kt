package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var activityNotifEnabled by remember { mutableStateOf(true) }
    var homeworkNotifEnabled by remember { mutableStateOf(true) }
    var gradeNotifEnabled by remember { mutableStateOf(true) }
    var homeworkReminderHours by remember { mutableStateOf(24f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Paramètres", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Activer les notifications")
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Nouveaux posts d'activité")
                Switch(
                    checked = activityNotifEnabled && notificationsEnabled,
                    onCheckedChange = { activityNotifEnabled = it },
                    enabled = notificationsEnabled
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Nouveau devoir à rendre")
                Switch(
                    checked = homeworkNotifEnabled && notificationsEnabled,
                    onCheckedChange = { homeworkNotifEnabled = it },
                    enabled = notificationsEnabled
                )
            }
            if (homeworkNotifEnabled && notificationsEnabled) {
                Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                    Text("Rappel avant échéance : ${homeworkReminderHours.toInt()}h")
                    Slider(
                        value = homeworkReminderHours,
                        onValueChange = { homeworkReminderHours = it },
                        valueRange = 1f..72f,
                        steps = 71
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Nouvelle note rentrée")
                Switch(
                    checked = gradeNotifEnabled && notificationsEnabled,
                    onCheckedChange = { gradeNotifEnabled = it },
                    enabled = notificationsEnabled
                )
            }
        }
    }
}