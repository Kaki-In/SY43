package e2su.nooble.api.models.objects

import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiSafeModel (
    val quota: Int,
    val decorations: List<String>,
    val badges: List<Pair<String, Int>>
)
