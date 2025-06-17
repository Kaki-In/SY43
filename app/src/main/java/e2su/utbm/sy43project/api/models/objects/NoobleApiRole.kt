package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoobleApiRole(val serialName: String, val description: String) {
    @SerialName("admin")
    ROLE_ADMIN("admin", "Administrator"),

    @SerialName("teacher")
    ROLE_TEACHER("teacher", "Teacher"),

    @SerialName("teacher_admin")
    ROLE_TEACHER_ADMIN("teacher_admin", "Teacher Administrator"),

    @SerialName("student")
    ROLE_STUDENT("student", "Student");

    override fun toString(): String = serialName
}

