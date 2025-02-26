package com.hussein.androidprojectstandard.domain.model

import kotlinx.serialization.Serializable

// Example business model
@Serializable
data class User(val id: Int, val name: String)
