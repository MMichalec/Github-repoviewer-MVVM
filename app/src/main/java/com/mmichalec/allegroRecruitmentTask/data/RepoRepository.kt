package com.mmichalec.allegroRecruitmentTask.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.room.withTransaction
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import com.mmichalec.allegroRecruitmentTask.ui.repositories.RepoViewModel
import com.mmichalec.allegroRecruitmentTask.util.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val repositoriesApi: RepositoriesApi, private val db : RepoDatabase
    ) {

    private val repoDao = db.repoDao()

    suspend fun getRepoDetail(repoName:String) = repositoriesApi.getRepo(repoName)

    fun getAllRepos(searchQuery: String, sortOrder:RepoViewModel.SortOrder) = networkBoundResource(
        query = {

            repoDao.getRepos(searchQuery, sortOrder)
        },
        fetch = {
           repositoriesApi.getReposFromApi()

        },
        saveFetchResult = {
            db.withTransaction {
                repoDao.deleteAllRepos()
                repoDao.insertRepos(it)
            }

        }
    )

}