package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.di

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.datasource.BarrilRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.datasource.BarrilRemoteDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.remotedatasource.GradeRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.remotedatasource.GradeRemoteDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.remotedatasource.ProducaoRemoteDatasource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.remotedatasource.ProducaoRemoteDatasourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.remotedatasource.ProdutoRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.remotedatasource.ProdutoRemoteDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.AuthDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.UsuarioRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.AuthDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.StorageDataSourceImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.datasource.UsuarioRemoteDataSourceImpl
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
    abstract fun bindStorageDataSource(impl: StorageDataSourceImpl): StorageDataSource

    @Binds
    @Singleton
    abstract fun bindUsuarioRemoteDataSource(impl: UsuarioRemoteDataSourceImpl): UsuarioRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindBarrilRemoteDataSource(impl: BarrilRemoteDataSourceImpl): BarrilRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProdutoRemoteDataSource(impl: ProdutoRemoteDataSourceImpl): ProdutoRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGradeRemoteDataSource(impl: GradeRemoteDataSourceImpl): GradeRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProducaoRemoteDataSource(impl: ProducaoRemoteDatasourceImpl): ProducaoRemoteDatasource

}