package e2su.nooble.api.models.objects

import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiAccountProfileModel(
    val firstName: @ParameterName("first_name") String,
    val lastName: @ParameterName("last_name") String,
    val activeDecoration: @ParameterName("active_decoration") String,
    val activeBadges: @ParameterName("active_badges") List<Pair<String, Int>>,
    val description: String,
    val classes: List<String>
)
