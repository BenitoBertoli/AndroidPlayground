package com.benitobertoli.androidplayground.domain.repository

import com.benitobertoli.androidplayground.core.SimpleResult
import com.benitobertoli.androidplayground.domain.model.Repo
import io.reactivex.Single

interface GithubRepository {
    fun getRepositories(): Single<SimpleResult<List<Repo>>>
}