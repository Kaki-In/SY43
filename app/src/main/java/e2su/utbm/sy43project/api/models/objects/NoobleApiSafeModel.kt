@file:UseSerializers(PairAsListSerializer::class)

package e2su.utbm.sy43project.api.models.objects

import e2su.utbm.sy43project.api.serializers.PairAsListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class NoobleApiSafeModel (
    @SerialName("quota") val quota: Int,
    @SerialName("decorations") val decorations: List<String>,
    @SerialName("badges") val badges: List<Pair<String, Int>>
)
