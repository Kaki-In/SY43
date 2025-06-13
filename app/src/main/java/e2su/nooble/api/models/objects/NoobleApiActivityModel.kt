package e2su.nooble.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiActivityModel (
    @SerialName("activity_id") val activityId: String,
    @SerialName("read") val read: Boolean,
    @SerialName("data") val data: NoobleApiActivityData
)
