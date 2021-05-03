package com.mmichalec.allegroRecruitmentTask.api

import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.data.RepoDetails
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoriesApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @Headers ("Accept: application/vnd.github.v3+json")
    @GET("orgs/allegro/repos")
    suspend fun searchRepo(
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,

        ): List<Repo>

    @GET("orgs/allegro/repos?per_page=100")
    suspend fun getReposFromApi(): List<Repo>

    @GET ("repos/allegro/{repoName}")
    suspend fun getRepo(@Path("repoName") type: String): RepoDetails
}