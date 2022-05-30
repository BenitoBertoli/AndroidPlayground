package com.benitobertoli.androidplayground.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.rxjava2.RxRemoteMediator
import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.core.ListMapperImpl
import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.mapper.*
import com.benitobertoli.androidplayground.data.network.di.NetworkModule
import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.data.network.service.GithubRemoteMediator
import com.benitobertoli.androidplayground.data.persistence.di.PersistenceModule
import com.benitobertoli.androidplayground.data.persistence.entity.OwnerEntity
import com.benitobertoli.androidplayground.data.persistence.entity.RepoEntity
import com.benitobertoli.androidplayground.data.persistence.entity.RepoWithOwner
import com.benitobertoli.androidplayground.data.repository.GithubRepositoryImpl
import com.benitobertoli.androidplayground.domain.model.Owner
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule.ProvidesModule::class, NetworkModule::class, PersistenceModule::class])
interface DataModule {

    @Binds
    fun bindRepoMapper(impl: RepoDtoToRepoMapper): Mapper<RepoDto, Repo>

    @Binds
    fun bindOwnerMapper(impl: OwnerDtoToOwnerMapper): Mapper<OwnerDto, Owner>

    @Binds
    fun bindRepoToRepoEntityMapper(impl: RepoToRepoEntityMapper): Mapper<Repo, RepoEntity>

    @Binds
    fun bindRepoToOwnerEntityMapper(impl: RepoToOwnerEntityMapper): Mapper<Repo, OwnerEntity>

    @Binds
    fun bindRepoWithOwnerToRepoMapper(impl: RepoWithOwnerToRepoMapper): Mapper<RepoWithOwner, Repo>

    @Binds
    fun bindGithubRepository(impl: GithubRepositoryImpl): GithubRepository

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    fun bindGithubRemoteMediator(impl: GithubRemoteMediator): RxRemoteMediator<Int, Repo>

    @Module
    object ProvidesModule {

        @Provides
        fun provideRepoListMapper(innerMapper: Mapper<RepoDto, Repo>): ListMapper<RepoDto, Repo> {
            return ListMapperImpl(innerMapper)
        }

        @Provides
        fun provideRepoWithOwnerListMapper(innerMapper: Mapper<RepoWithOwner, Repo>): ListMapper<RepoWithOwner, Repo> {
            return ListMapperImpl(innerMapper)
        }

    }

}