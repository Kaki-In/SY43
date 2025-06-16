@file:UseSerializers(InstantSerializer::class)

package e2su.utbm.sy43project.api.models.objects

import e2su.utbm.sy43project.api.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class NoobleApiActivityData(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("creator") val creator: String,
    @SerialName("date") val date: Instant,
    @SerialName("icon") val iconName: String
)
