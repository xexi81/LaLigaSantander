package com.los3molineros.laligasantander.presentation

import androidx.lifecycle.*
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.data.model.MatchFirestore
import com.los3molineros.laligasantander.domain.results.ResultsRepo
import kotlinx.coroutines.launch

class ResultsViewModel(private val repo: ResultsRepo) : ViewModel() {
    private val _matchList = MutableLiveData<Resource<List<MatchFirestore>?>>()
    val matchList: LiveData<Resource<List<MatchFirestore>?>> get() = _matchList

    private val _maxRound = MutableLiveData<Resource<Int?>>()
    val maxRound: LiveData<Resource<Int?>> get() = _maxRound

    private val _round = MutableLiveData<Resource<Int?>>()
    val round: LiveData<Resource<Int?>> get() = _round


    init {
        viewModelScope.launch {
            _round.postValue(Resource.Success(repo.getActualRound()))
            _matchList.postValue(Resource.Success(repo.getResults(repo.getActualRound())))
            _maxRound.postValue(Resource.Success(repo.getMaxRound()))
        }
    }

    fun otherRound(round: Int) {
        viewModelScope.launch {
            _round.postValue(Resource.Success(round))
            _matchList.postValue(Resource.Success(repo.getResults(round)))
        }
    }
}


class ResultsViewModelFactory(private val repo: ResultsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ResultsRepo::class.java).newInstance(repo)
    }
}







