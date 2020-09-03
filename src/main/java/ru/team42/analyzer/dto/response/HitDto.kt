package ru.team42.analyzer.dto.response

import ru.team42.analyzer.entities.ButtonEntity
import javax.persistence.Column
import javax.persistence.JoinColumn

data class HitDto(
        val id: Long?,
        val buttonId: Long,
        val url: String?,
        val action: String?,
        val data: String? = null
)