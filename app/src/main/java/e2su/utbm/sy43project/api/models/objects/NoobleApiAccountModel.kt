package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiAccountModel (
    @SerialName("id") val id: String,
    @SerialName("profile") val profile: NoobleApiAccountProfileModel,
    @SerialName("safe") val safe: NoobleApiSafeModel? = null,
    @SerialName("role") val role: NoobleApiRole,
    @SerialName("mail") val mail: String
)