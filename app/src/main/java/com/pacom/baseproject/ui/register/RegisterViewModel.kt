package com.pacom.baseproject.ui.register

import com.pacom.baseproject.base.BaseViewModel
import com.pacom.baseproject.network.AppDataManager
import com.pacom.baseproject.ui.validate.ValidateEmail
import com.pacom.baseproject.ui.validate.ValidatePassword
import com.pacom.baseproject.ui.validate.ValidateRepeatPassword
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel(
    private var appDataManager: AppDataManager
): BaseViewModel<RegisterNavigator>(appDataManager) {
    private val validateEmail = ValidateEmail()
    private val validatePassword = ValidatePassword()
    private val validateRepeatPassword = ValidateRepeatPassword()
    var state  = MutableStateFlow(RegistrationFormState())

    fun onEvent(event: RegistrationFormEvent) {
        when(event){
            is RegistrationFormEvent.EmailChange -> {
                state.value = RegistrationFormState(email = event.email)
            }

            is RegistrationFormEvent.PasswordChange -> {
                state.value = RegistrationFormState(password = event.password)
            }

            is RegistrationFormEvent.RepeatPasswordChange -> {
                state.value = RegistrationFormState(repeatPassword = event.repeatPass)
            }

            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData(){
        val emailResult = validateEmail.execute(state.value.email)
        val passwordResult = validatePassword.execute(state.value.password)
        val repeatPasswordResult = validateRepeatPassword.execute(state.value.password, state.value.repeatPassword)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatPasswordResult
        ).any { !it.successful }

        if(hasError){
            state.value = RegistrationFormState(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatPasswordError = repeatPasswordResult.errorMessage,
            )
            return
        }

    }
}