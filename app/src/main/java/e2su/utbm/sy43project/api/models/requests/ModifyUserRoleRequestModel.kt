package e2su.utbm.sy43project.api.models.requests

import e2su.utbm.sy43project.api.models.objects.NoobleApiRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyUserRoleRequestModel (
    @SerialName("user_id") val userId: String,
    @SerialName("role") val role: NoobleApiRole
)
