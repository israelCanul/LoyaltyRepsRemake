package com.xcaret.loyaltyreps.view.parks.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.usecase.ApiUseCase

class ParksViewModel: ViewModel() {
    var apiUseCase: ApiUseCase = ApiUseCase()
    var listParksTraining = MutableLiveData<List<XPark>?>(null)

    fun fetchTrainingData(){
        apiUseCase.fetchParksInfographic(){ list, error ->
            list?.let{
                listParksTraining.postValue(it)
            }?: run{
                listParksTraining.postValue(listOf())
            }
        }
    }
}