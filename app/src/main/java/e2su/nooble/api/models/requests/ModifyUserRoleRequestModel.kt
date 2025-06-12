package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyUserRoleRequestModel (
    @SerialName("user_id")
    val userId: String,
    val role: NoobleApiRole
)
