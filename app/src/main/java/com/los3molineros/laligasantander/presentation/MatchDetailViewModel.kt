package com.los3molineros.laligasantander.presentation

import androidx.lifecycle.*
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.domain.matchDetail.MatchDetailRepo
import kotlinx.coroutines.launch

class MatchDetailViewModel(private val repo: MatchDetailRepo) : ViewModel() {
    private val _matchDetail =
        MutableLiveData<Resource<com.los3molineros.laligasantander.data.model.MatchResult>>()
    val matchDetail: LiveData<Resource<com.los3molineros.laligasantander.data.model.MatchResult>> get() = _matchDetail

    fun getMatchData(matchId: Int) {
        viewModelScope.launch {
            _matchDetail.postValue(Resource.Success(repo.getMatchById(matchId).matchesList))
        }
    }
}

class MatchDetailViewModelFactory(private val repo: MatchDetailRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MatchDetailRepo::class.java).newInstance(repo)
    }
}