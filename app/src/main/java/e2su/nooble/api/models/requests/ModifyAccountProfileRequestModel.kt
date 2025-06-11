package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ModifyAccountProfileRequestModel (
    val userId: @ParameterName("user_id") String,
    val firstName: @ParameterName("first_name") String,
    val lastName: @ParameterName("last_name") String,
    val profileImage: @ParameterName("profile_image") String,
    val activeDecoration: @ParameterName("active_decoration") String,
    val activeBadges: @ParameterName("active_badges") List<String>,
    val description: String
)


