package com.sdm.mediacard.domain.use_case.validators

import com.sdm.mediacard.base.validation.DataValidator
import com.sdm.mediacard.base.validation.ValidationResult

class EmailValidator : DataValidator<String> {

    override fun isValid(dataToValidate: String): ValidationResult {

        return if (dataToValidate.isEmpty()) {
            ValidationResult.Failure("Please enter a email")//pass string msg or resource id & handle them according to type
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(dataToValidate)
                .matches()) {
            ValidationResult.Failure("Please enter a valid email")
        } else{
            ValidationResult.Success
        }

    }
}