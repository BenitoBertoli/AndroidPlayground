package com.benitobertoli.androidplayground.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class OwnerDaoTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var ownerDao: OwnerDao
    private lateinit var db: GithubDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        ownerDao = db.ownerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeOwner() {

        val owner = OwnerEntity(
            id = 1,
            name = "Benito",
            avatar = null
        )
        ownerDao.insert(owner).test().assertValue(owner.id)
    }
}