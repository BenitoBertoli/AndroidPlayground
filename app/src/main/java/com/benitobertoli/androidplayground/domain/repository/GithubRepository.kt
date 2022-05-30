package com.benitobertoli.androidplayground.domain.repository

import androidx.paging.PagingData
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import com.benitobertoli.androidplayground.domain.model.Repo
import io.reactivex.Flowable

interface GithubRepository {
    fun getRepositories(): Flowable<PagingData<RepoWithOwner>>
}