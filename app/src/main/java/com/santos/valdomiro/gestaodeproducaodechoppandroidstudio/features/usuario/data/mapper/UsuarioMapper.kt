package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.data.dto.UsuarioDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.usuario.domain.entity.UsuarioEntity

fun UsuarioDto.toEntity() : UsuarioEntity {
    return UsuarioEntity(
        id = this.id,
        nome = this.nome,
        sobrenome = this.sobrenome,
        email = this.email,
        urlFotoPerfil = this.urlFotoPerfil,
    );
}

fun UsuarioEntity.toDto() : UsuarioDto {
    return UsuarioDto(
        id = this.id,
        nome = this.nome,
        sobrenome = this.sobrenome,
        email = this.email,
        urlFotoPerfil = this.urlFotoPerfil,
    );
}