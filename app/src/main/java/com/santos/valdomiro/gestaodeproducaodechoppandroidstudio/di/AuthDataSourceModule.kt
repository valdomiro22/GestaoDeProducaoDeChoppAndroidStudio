package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.di

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.AuthDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.UsuarioRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.remote.AuthDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.remote.StorageDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.remote.UsuarioRemoteDataSourceImpl
import com.santos.valdomiro.gestaoproducaochopp.features.autenticacao.data.datasource.StorageDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDatasource(impl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindUsuarioRemoteDataSource(impl: UsuarioRemoteDataSourceImpl): UsuarioRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindStorageDataSource(impl: StorageDataSourceImpl): StorageDataSource
}