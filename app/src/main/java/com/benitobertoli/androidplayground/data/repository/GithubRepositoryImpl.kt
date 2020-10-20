package com.benitobertoli.androidplayground.data.repository

import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.core.SimpleResult
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.data.network.service.GithubService
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import io.reactivex.Single
import javax.inject.Inject

class GithubRepositoryImpl
@Inject constructor(
    private val githubService: GithubService,
    private val repoListMapper: ListMapper<RepoDto, Repo>
) : GithubRepository {
    override fun getRepositories(): Single<SimpleResult<List<Repo>>> {
        return githubService.searchRepositories()
            .map { result ->
                result.map {
                    repoListMapper.map(it.items)
                }
            }
    }
}