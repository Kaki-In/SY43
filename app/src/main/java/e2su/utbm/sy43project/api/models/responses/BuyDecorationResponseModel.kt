package e2su.utbm.sy43project.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BuyDecorationResponseModel (
    @SerialName("new_quota") val newQuota: Int
)

