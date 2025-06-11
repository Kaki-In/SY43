package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiRole
import kotlinx.serialization.Serializable

@Serializable
data class ModifyUserRoleRequestModel (
    val userId: @ParameterName("user_id") String,
    val role: NoobleApiRole
)
