package com.mmichalec.githubRepoviewerMVVM.api

import com.mmichalec.githubRepoviewerMVVM.data.Repo
import com.mmichalec.githubRepoviewerMVVM.data.RepoDetails
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoriesApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }


    @GET("users/{githubUser}/repos?per_page=100")
    suspend fun getReposFromApi(@Path("githubUser") type: String): List<Repo>

    @GET("repos/{githubUser}/{repoName}")
    suspend fun getRepo(
        @Path("githubUser") user: String,
        @Path("repoName") name: String,
        ): RepoDetails
}