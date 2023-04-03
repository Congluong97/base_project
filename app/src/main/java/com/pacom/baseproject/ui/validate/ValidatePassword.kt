package com.pacom.baseproject.ui.validate

import android.util.Patterns
import java.util.regex.Pattern

class ValidatePassword {

    fun execute(password: String): ValidationResult{
        if(password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "The passowrd needs to consist of at laeast 8 charater"
            )
        }
        val containsLettersAndDigit = password.any{it.isDigit()} && password.any{it.isLetter()}

        if(!containsLettersAndDigit){
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }

        return ValidationResult(
            successful = true,
        )
    }

}