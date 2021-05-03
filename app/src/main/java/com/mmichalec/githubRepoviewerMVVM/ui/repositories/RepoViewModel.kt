package com.mmichalec.githubRepoviewerMVVM.ui.repositories

import androidx.lifecycle.*
import com.mmichalec.githubRepoviewerMVVM.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RepoRepository) :
    ViewModel() {


    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)

    private val repoFlow = combine(
        searchQuery,
        sortOrder,

    ) {query, sortOrder ->
        Pair(query,sortOrder)

    }.flatMapLatest {(query,sortOrder) ->
        repository.getAllRepos(query,sortOrder, savedStateHandle.get<String>("githubUser").toString() )
    }
        val repositories = repoFlow.asLiveData()


        enum class SortOrder {BY_NAME, BY_CREATION_DATE, BY_UPDATE_DATE}

}