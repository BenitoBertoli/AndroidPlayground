package com.benitobertoli.androidplayground.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class RepoDaoTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repoDao: RepoDao
    private lateinit var db: GithubDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repoDao = db.repoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRepo() {

        val repo = RepoEntity(
            id = 1,
            name = "name",
            fullName = "full name",
            description = "description",
            stars = 500,
            forks = 10,
            language = "english",
            homepage = null,
            ownerId = 1
        )
        repoDao.insert(repo).test().assertValue(1)

        repoDao.findById(repo.id)
            .test()
            .assertValue {
                it.id == repo.id && it.name == repo.name
            }
    }
}