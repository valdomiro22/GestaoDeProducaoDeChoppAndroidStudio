package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.di

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository.AuthRepositoryImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository.StorageRepositoryImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.repository.UsuarioFirestoreFirestoreRepositoryImpl
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.AuthRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.StorageRepository
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.repository.UsuarioFirestoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(
        usuarioFirestoreRepositoryImpl: UsuarioFirestoreFirestoreRepositoryImpl
    ): UsuarioFirestoreRepository

    @Binds
    @Singleton
    abstract fun bindStorageRepository(
        storageRepositoryImpl: StorageRepositoryImpl
    ): StorageRepository
}