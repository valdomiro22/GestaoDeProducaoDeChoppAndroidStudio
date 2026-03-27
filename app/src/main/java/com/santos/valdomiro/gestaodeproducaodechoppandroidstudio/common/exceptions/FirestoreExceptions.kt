package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.exceptions

class AcessoNegadoException(
    causa: Throwable? = null
) : Exception("Sem permissão de acesso", causa)

class NaoEncontradoException(
    causa: Throwable? = null
) : Exception("Registro não encontrado", causa)

class ServicoIndisponivelException(
    causa: Throwable? = null
) : Exception("Serviço/Internet indisponível", causa)

class ErroBancoDadosDesconhecidoException(
    causa: Throwable? = null
) : Exception("Erro desconhecido no banco de dados", causa)