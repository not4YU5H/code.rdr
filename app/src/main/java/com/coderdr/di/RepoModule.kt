package com.coderdr.di

import com.coderdr.data.repo.ImplementMainRepo
import com.coderdr.domain.repo.MainRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMainRepo(
        implementMainRepo: ImplementMainRepo
    ): MainRepo
}