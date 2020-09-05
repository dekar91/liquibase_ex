package ru.team42.analyzer.dto.response

import ru.team42.analyzer.entities.IntegrationType

data class IntegrationDto(val id: Long?, val name: String?, val type: IntegrationType?, val settings: String? = null)