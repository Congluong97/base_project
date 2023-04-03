package com.pacom.baseproject.ui.validate

import android.util.Patterns
import java.util.regex.Pattern

class ValidateRepeatPassword {

    fun execute(password: String, repeatPassword: String): ValidationResult{
        if(password != repeatPassword){
            return ValidationResult(
                successful = false,
                errorMessage = "The password don't match"
            )
        }

        return ValidationResult(
            successful = true,
        )
    }

}