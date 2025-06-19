@file:UseSerializers(InstantSerializer::class)

package e2su.utbm.sy43project.api.models.responses

import e2su.utbm.sy43project.api.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class GetClassDataResponseModel (
    @SerialName("description") val description: String,
    @SerialName("last_modification") val lastModification: @ParameterName("last_modification") Instant,
    @SerialName("last_modifier") val lastModifier: @ParameterName("last_modifier") String,
    @SerialName("name") val name: String
)
