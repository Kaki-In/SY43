package e2su.utbm.sy43project.api.models.objects

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiActivityData(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("creator") val creator: String,
    @SerialName("date") val date: Instant,
    @SerialName("iconName") val iconName: String
)
