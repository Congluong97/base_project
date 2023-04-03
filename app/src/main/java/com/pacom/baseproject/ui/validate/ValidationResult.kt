package com.pacom.baseproject.ui.validate

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
