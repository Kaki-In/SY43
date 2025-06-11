package e2su.nooble.api.models.objects

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiActivityData(
    val title: String,
    val content: String,
    val creator: String,
    val date: Instant,

    @SerialName("iconName")
    val iconName: String
)
