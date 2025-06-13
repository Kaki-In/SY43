package e2su.nooble.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiAccountModel (
    @SerialName("id") val id: String,
    @SerialName("profile") val profile: NoobleApiAccountProfileModel,
    @SerialName("safe") val safe: NoobleApiSafeModel,
    @SerialName("role") val role: NoobleApiRole,
    @SerialName("mail") val mail: String
)