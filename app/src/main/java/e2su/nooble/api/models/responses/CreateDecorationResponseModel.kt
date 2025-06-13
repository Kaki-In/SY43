package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiBadgeModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateDecorationResponseModel (
    @SerialName("new_decoration") val newDecoration: String

)

