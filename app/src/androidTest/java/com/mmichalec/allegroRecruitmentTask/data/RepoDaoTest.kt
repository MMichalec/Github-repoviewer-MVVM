package com.mmichalec.allegroRecruitmentTask.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mmichalec.allegroRecruitmentTask.ui.repositories.RepoViewModel
import com.mmichalec.getOrAwaitValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
@SmallTest //unit test
class RepoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private  lateinit var database: RepoDatabase
    private lateinit var dao: RepoDao
    private lateinit var r1:Repo
    private lateinit var r2:Repo
    private lateinit var r3:Repo
    private lateinit var listOfRepo: List<Repo>

    @Before
    fun setup(){
        //in memory - not real database. Just stored in ram not in persistece storage.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepoDatabase::class.java
        ).allowMainThreadQueries().build() // you can run on main thread because there it's a test and will not block anything
        dao = database.repoDao()

        r1 = Repo(1,"Aba",null,"2020","2020")
        r2 = Repo(2,"aaa",null,"2018","2018")
        r3 = Repo(3,"xcd",null,"2019","2019")
        listOfRepo = listOf(r1,r2,r3)


    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertRepo()= runBlockingTest {
        dao.insertRepo(r1)
        val allRepos = dao.observeAllRepos().getOrAwaitValue()
        assertThat(allRepos).contains(r1)
    }


    @Test
    fun searchTest() = runBlockingTest{

        dao.insertRepo(r1)
        dao.insertRepo(r2)
        dao.insertRepo(r3)

       val f = dao.getReposSortedByName("a").first()
        val f2 = dao.getReposSortedByName("x").first()
        assertThat(f).contains(r1)
        assertThat(f).contains(r2)
        assertThat(f).doesNotContain(r3)

        assertThat(f2).doesNotContain(r2)
        assertThat(f2).doesNotContain(r1)
        assertThat(f2).contains(r3)
        }

    @Test
    fun sortByNameTest() = runBlockingTest {
        dao.insertRepo(r1)
        dao.insertRepo(r2)
        dao.insertRepo(r3)

        val f = dao.getReposSortedByName("").first()

        assertThat(f[0]).isEqualTo(r2)
        assertThat(f[1]).isEqualTo(r1)
        assertThat(f[2]).isEqualTo(r3)
    }

    @Test
    fun sortByNameCreatedDate() = runBlockingTest {
        dao.insertRepo(r1)
        dao.insertRepo(r2)
        dao.insertRepo(r3)

        val f = dao.getReposSortedByDateCreated("").first()

        assertThat(f[0]).isEqualTo(r1)
        assertThat(f[1]).isEqualTo(r3)
        assertThat(f[2]).isEqualTo(r2)
    }

    @Test
    fun sortByNameUpdateDate() = runBlockingTest {
        dao.insertRepo(r1)
        dao.insertRepo(r2)
        dao.insertRepo(r3)

        val f = dao.getReposSortedByDateUpdated("").first()

        assertThat(f[0]).isEqualTo(r1)
        assertThat(f[1]).isEqualTo(r3)
        assertThat(f[2]).isEqualTo(r2)
    }

}