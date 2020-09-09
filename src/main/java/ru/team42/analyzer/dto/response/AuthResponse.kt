package ru.team42.analyzer.dto.response

/**
 *
 */
data class AuthResponse (
        val username:String,
        /**
         * JWT token
         */
        val token:String,
)