package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiBadgeModel
import kotlinx.serialization.Serializable

@Serializable
class CreateDecorationResponseModel (
    val newDecoration: @ParameterName("new_decoration") String

)

