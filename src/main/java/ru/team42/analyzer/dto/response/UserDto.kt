package ru.team42.analyzer.dto.response

import java.util.*
import javax.persistence.Column
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class UserDto (
    val id: Long? = null,
    val username: @NotNull @Size(min = 3, message = "Не меньше 3 знаков") String? = null,
    val password: String? = null,
    val roles: Set<RoleDto>? = Collections.emptySet(),
    val chat2DeskId: Long? = null,
)