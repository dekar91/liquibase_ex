package ru.team42.analyzer.dto

import javax.validation.constraints.NotBlank

data class HitRequest (
        @NotBlank val buttonId:String,
        @NotBlank val url: String,
        val action: String,

        val data: String? = null
)