package com.pacom.baseproject.ui.mp3

import com.pacom.baseproject.base.BaseViewModel
import com.pacom.baseproject.network.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Mp3ViewModel @Inject constructor(
    private val appDataManager: AppDataManager
): BaseViewModel<Mp3Navigator>(appDataManager){
}