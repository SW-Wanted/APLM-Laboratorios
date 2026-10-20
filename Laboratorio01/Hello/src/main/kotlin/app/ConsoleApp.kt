package org.emanueldossantos.app

import org.emanueldossantos.model.Movimento
import org.emanueldossantos.model.Sessao
import org.emanueldossantos.service.GestorUtilizadores

class ConsoleApp {

    private val gestor = GestorUtilizadores()
    private var sessaoAtual: Sessao? = null

    fun iniciar() {
        while (true) {
            val comando = readlnOrNull()?.trim() ?: break

            if (comando == "exit") {
                sessaoAtual?.let { sessao ->
                    gestor.encerrarSessao(sessao.token)
                    sessaoAtual = null
                }
                continue
            }

            executarComando(comando)
        }
    }

    private fun executarComando(comando: String) {
        if (comando.isBlank()) return

        val argumentos = comando.split(Regex("\\s+"))

        if (argumentos[0] == "u") {
            iniciarSessao(argumentos)
            return
        }

        val sessao = sessaoAtual

        if (sessao == null) {
            println("cmd: inicie sessão com 'u email password'")
            return
        }

        val orcamento = gestor.obterOrcamento(sessao) ?: return

        when (argumentos[0]) {
            "i" -> {
                if (argumentos.size < 6) return

                val movimento = Movimento(
                    montante = argumentos[1].toInt(),
                    data = argumentos[2].toInt(),
                    periodicidade = argumentos[3].toInt(),
                    tipo = argumentos[4][0],
                    descricao = argumentos[5]
                )

                orcamento.inserir(movimento)
            }

            "l" -> {
                if (argumentos.size < 2) return

                orcamento.listar(argumentos[1].toInt())
                    .forEachIndexed { indice, movimento ->
                        println(
                            "$indice ${movimento.montante} " +
                                    "${movimento.tipo} ${movimento.descricao}"
                        )
                    }
            }

            "r" -> {
                if (argumentos.size < 3) return

                orcamento.remover(
                    data = argumentos[1].toInt(),
                    indice = argumentos[2].toInt()
                )
            }

            "b" -> {
                if (argumentos.size < 2) return

                println(orcamento.balanco(argumentos[1].toInt()))
            }

            "m" -> {
                if (argumentos.size < 2) return

                orcamento.definirMinimoOrcamento(
                    argumentos[1].toInt()
                )
            }

            "v" -> {
                val valido = orcamento.verificar()
                println(if (valido) "Valido" else "Invalido")
            }

            "w" -> {
                if (argumentos.size < 3) return

                val data = argumentos[1].toInt()
                val despesa = argumentos[2].toInt()
                val dataComportavel = orcamento.comportavel(data, despesa)

                println(dataComportavel ?: "Incomportavel")
            }

            else -> {
                println("cmd: comando desconhecido!")
            }
        }
    }

    private fun iniciarSessao(argumentos: List<String>) {
        if (sessaoAtual != null) {
            println("Já existe uma sessão activa.")
            return
        }

        if (argumentos.size != 3) {
            println("Utilização: u email password")
            return
        }

        val email = argumentos[1]
        val palavraPasse = argumentos[2]

        val sessao = gestor.iniciarSessao(email, palavraPasse)

        if (sessao == null) {
            println("Credenciais inválidas.")
        } else {
            sessaoAtual = sessao
        }
    }
}
