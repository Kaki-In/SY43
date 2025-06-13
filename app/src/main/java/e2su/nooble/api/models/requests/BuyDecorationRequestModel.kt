package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuyDecorationRequestModel (
    @SerialName("decoration") val decoration: String
)
