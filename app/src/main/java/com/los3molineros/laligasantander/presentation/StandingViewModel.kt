package com.los3molineros.laligasantander.presentation

import androidx.lifecycle.*
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.data.model.StandingsUI
import com.los3molineros.laligasantander.domain.standings.StandingsRepo
import kotlinx.coroutines.launch

class StandingViewModel(private val repo: StandingsRepo) : ViewModel() {
    private val _standingUIList = MutableLiveData<Resource<List<StandingsUI>>>()
    val standingUIList: LiveData<Resource<List<StandingsUI>>> get() = _standingUIList

    init {
        viewModelScope.launch {
            _standingUIList.postValue(Resource.Success(repo.getStandings()))
        }
    }
}


class StandingViewModelFactory(private val repo: StandingsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(StandingsRepo::class.java).newInstance(repo)
    }

}



