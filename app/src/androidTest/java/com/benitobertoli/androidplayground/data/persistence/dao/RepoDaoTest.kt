package com.benitobertoli.androidplayground.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class RepoDaoTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var ownerDao: OwnerDao
    private lateinit var db: GithubDatabase

    private lateinit var sut: RepoDao

    private val owner = OwnerEntity(
        id = 5,
        name = "Benito",
        avatar = null
    )

    private val repo = RepoEntity(
        id = 1,
        name = "name",
        fullName = "full name",
        description = "description",
        stars = 500,
        forks = 10,
        language = "english",
        homepage = null,
        ownerId = owner.id
    )

    private fun createOwner(id: Long) = OwnerEntity(id, "mock_owner", null)
    private fun createRepo(id: Long, ownerId: Long) = RepoEntity(
        id, "mock_name", "mock_full_name", "mock_description",
        10, 5, "mock_lang", "https://www.mock.info", ownerId
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        sut = db.repoDao()
        ownerDao = db.ownerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRepo() {
        val result = sut.insert(repo)
        assertThat(result).isEqualTo(repo.id)
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        ownerDao.insert(owner)
        sut.insert(repo)

        sut.findById(repo.id).test().assertValue {
            it.repoId == repo.id
                    && it.repoName == repo.name
                    && it.repoFullName == repo.fullName
                    && it.repoDescription == repo.description
                    && it.repoStars == repo.stars
                    && it.repoForks == repo.forks
                    && it.repoLanguage == repo.language
                    && it.repoHomepage == repo.homepage
                    && it.ownerId == owner.id
                    && it.ownerName == owner.name
                    && it.ownerAvatar == owner.avatar
        }
    }
}