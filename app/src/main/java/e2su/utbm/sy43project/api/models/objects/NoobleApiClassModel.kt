@file:UseSerializers(InstantSerializer::class)

package e2su.utbm.sy43project.api.models.objects

import e2su.utbm.sy43project.api.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.JsonObject

@Serializable
data class NoobleApiClassModel (
    @SerialName("id") val id: String,
    @SerialName("content") val content: JsonObject,
    @SerialName("description") val description: String,
    @SerialName("lastModification") val lastModification: @ParameterName("last_modification") Instant,
    @SerialName("lastModifier") val lastModifier: @ParameterName("last_modifier") String,
    @SerialName("name") val name: String
)