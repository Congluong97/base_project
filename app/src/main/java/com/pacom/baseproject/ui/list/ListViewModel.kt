package com.pacom.baseproject.ui.list

import com.pacom.baseproject.base.BaseViewModel
import com.pacom.baseproject.network.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    appDataManager: AppDataManager
) : BaseViewModel<ListNavigator>(appDataManager){
}