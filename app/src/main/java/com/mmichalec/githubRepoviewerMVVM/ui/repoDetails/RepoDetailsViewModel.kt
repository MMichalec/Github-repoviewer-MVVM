package com.mmichalec.githubRepoviewerMVVM.ui.repoDetails

import androidx.lifecycle.*
import com.mmichalec.githubRepoviewerMVVM.data.RepoDetails
import com.mmichalec.githubRepoviewerMVVM.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val repository: RepoRepository,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val repoDetailsLiveData = MutableLiveData<RepoDetails>()
    val repoDetails: LiveData<RepoDetails> = repoDetailsLiveData
    private val githubUser = savedStateHandle.get<String>("githubUser")
    private val repoName = savedStateHandle.get<String>("repoName")




    init {
        println(repoName)
        viewModelScope.launch {
            val repoDetails = repoName?.let { repository.getRepoDetail(githubUser!!,repoName) }
            repoDetailsLiveData.value = repoDetails!!
        }
    }
}