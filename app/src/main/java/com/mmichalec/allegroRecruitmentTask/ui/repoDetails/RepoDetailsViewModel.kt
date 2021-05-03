package com.mmichalec.allegroRecruitmentTask.ui.repoDetails

import android.content.Context
import androidx.lifecycle.*
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.data.RepoDetails
import com.mmichalec.allegroRecruitmentTask.data.RepoRepository
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
    private val repoName = savedStateHandle.get<String>("repoName")


    init {
        println(repoName)
        viewModelScope.launch {
            val repoDetails = repoName?.let { repository.getRepoDetail(repoName) }
            repoDetailsLiveData.value = repoDetails!!


        }
    }
}