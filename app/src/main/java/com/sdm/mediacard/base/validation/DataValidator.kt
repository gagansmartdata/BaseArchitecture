package com.sdm.mediacard.base.validation

/**
 * base of all the data validators.
 */
interface DataValidator<G> {
    fun isValid(dataToValidate : G) : ValidationResult
}