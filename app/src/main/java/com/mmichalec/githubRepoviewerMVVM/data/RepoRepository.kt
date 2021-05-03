package com.mmichalec.githubRepoviewerMVVM.data

import android.util.Log
import androidx.room.withTransaction
import com.mmichalec.githubRepoviewerMVVM.api.RepositoriesApi
import com.mmichalec.githubRepoviewerMVVM.ui.repositories.RepoViewModel
import com.mmichalec.githubRepoviewerMVVM.util.Resource
import com.mmichalec.githubRepoviewerMVVM.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val repositoriesApi: RepositoriesApi, private val db : RepoDatabase
    ) {

    private val repoDao = db.repoDao()
    suspend fun getRepoDetail(githubUser: String, repoName:String) = repositoriesApi.getRepo(githubUser, repoName)
    private var time = 0L
    private var lastUser = ""
    private var resetDisplay = false


    fun getAllRepos(searchQuery: String, sortOrder:RepoViewModel.SortOrder, githubUser: String): Flow<Resource<List<Repo>>> {
        if(lastUser == "" || lastUser!= githubUser){
            lastUser = githubUser
            time=0L
        }
        return networkBoundResource(
            query = {
                Log.d("Repository","DB called")
                repoDao.getRepos(searchQuery, sortOrder)

            },
            fetch = {
                time = System.currentTimeMillis()
                Log.d("Repository","Api called")
                repositoriesApi.getReposFromApi(githubUser)
            },
            shouldFetch = {
                          System.currentTimeMillis() - time > 60000
            },
            saveFetchResult = {
                //transactions means that all must be executed. If one fails none will happen
                db.withTransaction {
                    repoDao.deleteAllRepos()
                    repoDao.insertRepos(it)
                }

            }
        )
    }

}