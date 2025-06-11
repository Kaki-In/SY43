package e2su.nooble.api.models.objects

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiActivityData(
    val title: String,
    val content: String,
    val creator: String,
    val date: Instant,
    val iconName: @ParameterName("icon") String
)
