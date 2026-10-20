
package org.emanueldossantos.service

import org.emanueldossantos.model.Movimento

class Orcamento {

    private val movimentos = mutableListOf<Movimento>()
    private var minimoOrcamento: Int = 0

    fun inserir(movimento: Movimento) {
        var dataAtual = movimento.data

        do {
            movimentos.add(
                Movimento(
                    montante = movimento.montante,
                    data = dataAtual,
                    periodicidade = 0,
                    tipo = movimento.tipo,
                    descricao = movimento.descricao
                )
            )

            if (movimento.periodicidade == 0) break

            dataAtual += movimento.periodicidade
        } while (dataAtual <= 999)
    }

    fun listar(data: Int): List<Movimento> {
        return movimentos.filter { it.data == data }
    }

    fun remover(data: Int, indice: Int) {
        val movimentosDaData = listar(data)

        if (indice in movimentosDaData.indices) {
            movimentos.remove(movimentosDaData[indice])
        }
    }

    fun balanco(data: Int): Int {
        return movimentos
            .filter { it.data <= data }
            .sumOf { movimento ->
                if (movimento.tipo == 'r') {
                    movimento.montante
                } else {
                    -movimento.montante
                }
            }
    }

    fun definirMinimoOrcamento(valor: Int) {
        minimoOrcamento = valor
    }

    private fun calcularBalancos(): IntArray {
        val movimentosPorData = IntArray(1000)
        val balancos = IntArray(1000)

        for (movimento in movimentos) {
            movimentosPorData[movimento.data] +=
                if (movimento.tipo == 'r') {
                    movimento.montante
                } else {
                    -movimento.montante
                }
        }

        var acumulado = 0

        for (data in 0..999) {
            acumulado += movimentosPorData[data]
            balancos[data] = acumulado
        }

        return balancos
    }

    fun verificar(): Boolean {
        val balancos = calcularBalancos()
        return balancos.all { it >= minimoOrcamento }
    }

    fun comportavel(data: Int, despesa: Int): Int? {
        val balancos = calcularBalancos()

        for (dataCandidata in data..999) {
            var possivel = true

            for (dataFutura in dataCandidata..999) {
                if (balancos[dataFutura] - despesa < minimoOrcamento) {
                    possivel = false
                    break
                }
            }

            if (possivel) {
                return dataCandidata
            }
        }

        return null
    }
}