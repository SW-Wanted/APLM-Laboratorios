package org.emanueldossantos.model

class Utilizador(
    val email: String,
    internal val palavraPasse: String
) {
    val sessoes = mutableListOf<Sessao>()
}
