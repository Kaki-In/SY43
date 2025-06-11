package e2su.nooble.api.models.objects

import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiActivityModel (
    val activityId: @ParameterName("activity_id") String,
    val read: Boolean,
    val data: NoobleApiActivityData
)
