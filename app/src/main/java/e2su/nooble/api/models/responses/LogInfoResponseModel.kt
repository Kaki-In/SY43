package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiAccountModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogInfoResponseModel (
    @SerialName("connected") val connected: Boolean,
    @SerialName("account") val account: NoobleApiAccountModel? = null
)
