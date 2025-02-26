package com.hussein.androidprojectstandard.presentation.home

import com.hussein.androidprojectstandard.presentation.base.BaseUIState
import com.hussein.androidprojectstandard.utils.UIText

data class HomeScreenState(
    override val isLoading: Boolean = false,
    override val errorMessage: UIText? = null,
): BaseUIState()