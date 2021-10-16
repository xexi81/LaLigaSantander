package com.los3molineros.laligasantander.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.domain.MainRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val repo: MainRepo): ViewModel() {
    fun searchLeagueId() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.getData()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}


class MainViewModelFactory(private val repo: MainRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MainRepo::class.java).newInstance(repo)
    }

}