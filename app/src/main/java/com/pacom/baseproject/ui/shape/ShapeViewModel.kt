package com.pacom.baseproject.ui.shape

import com.pacom.baseproject.base.BaseViewModel
import com.pacom.baseproject.network.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShapeViewModel @Inject constructor(
    private val appDataManager: AppDataManager
): BaseViewModel<ShapeNavigator>(appDataManager){
}