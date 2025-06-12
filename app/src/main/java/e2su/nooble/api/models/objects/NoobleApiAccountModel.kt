package e2su.nooble.api.models.objects

import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiAccountModel (
    val id: String,
    val profile: NoobleApiAccountProfileModel,
    val safe: NoobleApiSafeModel,
    val role: NoobleApiRole,
    val mail: String
)