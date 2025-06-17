package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoobleApiResourceType(val typename: String) {
    @SerialName("profile icon")
    RESOURCE_TYPE_PROFILE_ICON("profile icon"),

    @SerialName("section file")
    RESOURCE_TYPE_SECTION_FILE("section file"),

    @SerialName("decoration banner")
    RESOURCE_TYPE_DECORATION_BANNER("decoration banner");

    override fun toString(): String = typename

}