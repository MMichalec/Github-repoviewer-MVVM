package com.mmichalec.allegroRecruitmentTask.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mmichalec.allegroRecruitmentTask.ui.repositories.RepoViewModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    fun getRepos(query:String, sortOrder: RepoViewModel.SortOrder): Flow<List<Repo>> =
        when(sortOrder){
            RepoViewModel.SortOrder.BY_NAME -> getReposSortedByName(query)
            RepoViewModel.SortOrder.BY_CREATION_DATE -> getReposSortedByDateCreated(query)
            RepoViewModel.SortOrder.BY_UPDATE_DATE -> getReposSortedByDateUpdated(query)
        }

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by LOWER(name) ASC, name ")
    fun getReposSortedByName(searchQuery: String): Flow<List<Repo>>

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by created_at DESC, created_at ")
    fun getReposSortedByDateCreated(searchQuery: String): Flow<List<Repo>>

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by updated_at DESC, updated_at ")
    fun getReposSortedByDateUpdated(searchQuery: String): Flow<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos : List<Repo>)

    @Query("DELETE FROM  repositories")
    suspend fun deleteAllRepos(){}

    @Delete
    suspend fun deleteRepo(repo:Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo:Repo)

    @Query("SELECT * FROM repositories")
    fun observeAllRepos(): LiveData<List<Repo>>

}