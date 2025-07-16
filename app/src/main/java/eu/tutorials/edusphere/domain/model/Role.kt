package eu.tutorials.edusphere.domain.model

enum class Role {
    STUDENT, INSTRUCTOR, ADMIN;

    companion object {
        fun fromString(role: String): Role {
            return when (role.uppercase()) {
                "STUDENT" -> STUDENT
                "INSTRUCTOR" -> INSTRUCTOR
                "ADMIN" -> ADMIN
                else -> STUDENT
            }
        }
    }
}