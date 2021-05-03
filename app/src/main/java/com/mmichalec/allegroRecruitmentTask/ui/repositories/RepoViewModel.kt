package com.mmichalec.allegroRecruitmentTask.ui.repositories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: RepoRepository) :
    ViewModel() {


    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)

    private val repoFlow = combine(
        searchQuery,
        sortOrder

    ) {query, sortOrder ->
        Pair(query,sortOrder)

    }.flatMapLatest {(query,sortOrder) ->
        repository.getAllRepos(query,sortOrder)
    }
        val repositories = repoFlow.asLiveData()


        enum class SortOrder {BY_NAME, BY_CREATION_DATE, BY_UPDATE_DATE}

}