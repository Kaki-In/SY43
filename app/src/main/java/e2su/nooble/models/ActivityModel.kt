package e2su.nooble.models

import java.util.Date

data class ActivityModel(
    val title: String,
    val date: Date,
    val sender: ProfileModel,
    val icon: Int,
    val isRead: Boolean,
    val content: String
)

