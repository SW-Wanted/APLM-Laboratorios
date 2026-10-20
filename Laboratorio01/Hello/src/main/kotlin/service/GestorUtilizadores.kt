package org.emanueldossantos.service

import org.emanueldossantos.model.Sessao
import org.emanueldossantos.model.Utilizador

class GestorUtilizadores {

    private val utilizadores = mutableMapOf<String, Utilizador>()
    private val orcamentos = mutableMapOf<String, Orcamento>()
    private val sessoesAtivas = mutableMapOf<String, Sessao>()

    fun iniciarSessao(email: String, palavraPasse: String): Sessao? {
        if (email.isBlank() || palavraPasse.isBlank()) {
            return null
        }

        val utilizadorExistente = utilizadores[email]

        val utilizador = if (utilizadorExistente == null) {
            val novoUtilizador = Utilizador(email, palavraPasse)

            utilizadores[email] = novoUtilizador
            orcamentos[email] = Orcamento()

            novoUtilizador
        } else {
            if (utilizadorExistente.palavraPasse != palavraPasse) {
                return null
            }

            utilizadorExistente
        }

        val sessao = Sessao()

        utilizador.sessoes.add(sessao)
        sessoesAtivas[sessao.token] = sessao

        return sessao
    }

    fun obterUtilizador(sessao: Sessao): Utilizador? {
        return utilizadores.values.find { utilizador ->
            utilizador.sessoes.any { it.token == sessao.token } &&
                    sessoesAtivas.containsKey(sessao.token)
        }
    }

    fun obterOrcamento(sessao: Sessao): Orcamento? {
        val utilizador = obterUtilizador(sessao) ?: return null
        return orcamentos[utilizador.email]
    }

    fun encerrarSessao(token: String): Boolean {
        return sessoesAtivas.remove(token) != null
    }

    fun listarUtilizadores(): List<Utilizador> {
        return utilizadores.values.toList()
    }
}
