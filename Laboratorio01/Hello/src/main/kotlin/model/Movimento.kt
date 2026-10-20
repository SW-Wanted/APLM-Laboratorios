package org.emanueldossantos.model

data class Movimento(
    val montante: Int,
    val data: Int,
    val periodicidade: Int,
    val tipo: Char,
    val descricao: String
)
