package com.pacom.baseproject

import android.util.Log
import androidx.lifecycle.*
import com.pacom.baseproject.base.BaseViewModel
import com.pacom.baseproject.mode.reponse.Bodys
import com.pacom.baseproject.network.AppDataManager
import com.pacom.baseproject.utils.parseGson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

private const val TAG = "MainViewModel"
@HiltViewModel
class MainViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<MainNavigator>(appDataManager) {
//    val liveData: LiveData<ConstantResponse> = liveData {
//        appDataManager.getConstant()
//            .catch {
//                Log.e(TAG, "callApi: ${it.message}", )
//            }
//            .collect{
//            emit(it)
//        }
//    }

    val liveData = MutableLiveData<Bodys>()

     suspend fun callApi() {
            appDataManager.getConstant()
                .catch { throwable ->
                    getNavigator()?.handleError(throwable)
                }
                .collect { response->
                    Log.d(TAG, "callApi: ${response.parseGson()}")
                    liveData.value = response.body
            }

    }

    fun openActivity(){
        getNavigator()?.openListFragment()
    }

    fun openDialog(){
        getNavigator()?.openDialog()
    }

    fun openShape(){
        getNavigator()?.openShape()
    }

    fun mp3(){
        getNavigator()?.mp3()
    }
}