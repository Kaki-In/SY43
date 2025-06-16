package e2su.utbm.sy43project.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddAccountResponseModel (
    @SerialName("new_account") val newAccountId: String
)
