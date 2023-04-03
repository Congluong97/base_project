package com.pacom.baseproject.ui.register

sealed class RegistrationFormEvent{
    data class EmailChange(val email: String): RegistrationFormEvent()
    data class PasswordChange(val password: String): RegistrationFormEvent()
    data class RepeatPasswordChange(val repeatPass: String): RegistrationFormEvent()
    object Submit : RegistrationFormEvent()
}
