package com.hussein.androidprojectstandard.presentation.base

import com.hussein.androidprojectstandard.utils.UIText

abstract class BaseUIState(
    open val isLoading: Boolean = false,
    open val errorMessage: UIText? = null
)