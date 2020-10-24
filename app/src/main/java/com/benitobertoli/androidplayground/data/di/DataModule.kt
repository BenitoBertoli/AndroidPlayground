package com.benitobertoli.androidplayground.data.di

import com.benitobertoli.androidplayground.core.ListMapper
import com.benitobertoli.androidplayground.core.ListMapperImpl
import com.benitobertoli.androidplayground.core.Mapper
import com.benitobertoli.androidplayground.data.mapper.OwnerMapper
import com.benitobertoli.androidplayground.data.mapper.RepoMapper
import com.benitobertoli.androidplayground.data.network.di.NetworkModule
import com.benitobertoli.androidplayground.data.network.dto.OwnerDto
import com.benitobertoli.androidplayground.data.network.dto.RepoDto
import com.benitobertoli.androidplayground.data.repository.GithubRepositoryImpl
import com.benitobertoli.androidplayground.domain.model.Owner
import com.benitobertoli.androidplayground.domain.model.Repo
import com.benitobertoli.androidplayground.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule.ProvidesModule::class, NetworkModule::class])
interface DataModule {

    @Binds
    fun bindRepoMapper(impl: RepoMapper): Mapper<RepoDto, Repo>

    @Binds
    fun bindOwnerMapper(impl: OwnerMapper): Mapper<OwnerDto, Owner>

    @Binds
    fun bindGithubRepository(impl: GithubRepositoryImpl): GithubRepository

    @Module
    object ProvidesModule {

        @Provides
        fun provideRepoListMapper(innerMapper: Mapper<RepoDto, Repo>): ListMapper<RepoDto, Repo> {
            return ListMapperImpl(innerMapper)
        }

    }

}