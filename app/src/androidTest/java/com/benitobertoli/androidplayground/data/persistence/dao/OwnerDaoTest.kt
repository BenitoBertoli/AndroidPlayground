package com.benitobertoli.androidplayground.data.persistence.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.benitobertoli.androidplayground.data.persistence.GithubDatabase
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class OwnerDaoTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sut: OwnerDao
    private lateinit var db: GithubDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        sut = db.ownerDao()
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
        val result = sut.insert(owner)
        assertThat(result).isEqualTo(owner.id)
    }

    @Test
    @Throws(Exception::class)
    fun deleteOwners() {

        val owner1 = OwnerEntity(
            id = 1,
            name = "Benito",
            avatar = null
        )
        val owner2 = OwnerEntity(
            id = 2,
            name = "May",
            avatar = null
        )
        sut.insert(owner1)
        sut.insert(owner2)

        val rows = sut.clearAll()
        assertThat(rows).isEqualTo(2)
    }
}