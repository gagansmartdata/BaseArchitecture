package com.sdm.mediacard.base

/**
 * Base data class for UI state
 */
data class UIState<G>(
    val isLoading: Boolean = false,
    val data: G? = null,
    val error: Any? = null
)

