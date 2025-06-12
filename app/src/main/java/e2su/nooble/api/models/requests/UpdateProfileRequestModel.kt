package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequestModel (
    @SerialName("first_name")
    val firstName: @ParameterName("first_name") String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("profile_image")
    val profileImage: String,

    @SerialName("active_decoration")
    val activeDecoration: String,

    @SerialName("active_badges")
    val activeBadges: List<String>,

    val description: String
)


