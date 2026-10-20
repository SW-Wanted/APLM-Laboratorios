package org.emanueldossantos

data class Movimento(
    val montante: Int,
    val data: Int,
    val periodicidade: Int,
    val tipo: Char,
    val descricao: String
)

val lista = mutableListOf<Movimento>()
var valueMinimo: Int = 0

fun main() {
    while (true) {
        val cmd = readln().trim()
        if (cmd == "exit")
            break
        execCommand(cmd)
    }
}

fun execCommand(cmd: String) {
    if (cmd.isBlank()) return
    val args = cmd.trim().split(Regex("\\s+"))
    when (args[0]) {
        "i" -> inserir(
            Movimento(
                args[1].toInt(),
                args[2].toInt(),
                args[3].toInt(),
                args[4][0],
                args[5]
            )
        )
        "l" -> listar(args[1].toInt())
        "r" -> remover(
            args[1].toInt(),
            args[2].toInt()
        )
        "b" -> println(balanco(args[1].toInt()))
        "m" -> setValorMinimo(args[1].toInt())
        "v" -> verificar()
        "w" -> comportavel(
            args[1].toInt(),
            args[2].toInt()
        )
        else -> println("cmd: comando desconhecido!")
    }
}

fun inserir(movimento: Movimento) {
    var dataAtual = movimento.data
    do {
        lista.add(
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

fun listar(data: Int) {
    val movimentos = lista.filter { it.data == data }
    movimentos.forEachIndexed { indice, movimento ->
        println("$indice ${movimento.montante} ${movimento.tipo} ${movimento.descricao}")
    }
}

fun remover(data: Int, indice: Int) {
    val movimentos = lista.filter { it.data == data }
    if (indice in movimentos.indices)
        lista.remove(movimentos[indice])
}

fun balanco(data: Int): Int {
    return lista
        .filter { it.data <= data }
        .sumOf {
            if (it.tipo == 'r')
                it.montante
            else -it.montante
        }
}

fun setValorMinimo(valor: Int) {
    valueMinimo = valor
}

fun calcularBalancos(): IntArray {
    val movimentosPorData = IntArray(1000)
    val balancos = IntArray(1000)
    for (movimento in lista) {
        movimentosPorData[movimento.data] +=
            if (movimento.tipo == 'r')
                movimento.montante
            else -movimento.montante
    }
    var acumulado = 0
    for (data in 0..999) {
        acumulado += movimentosPorData[data]
        balancos[data] = acumulado
    }
    return balancos
}

fun verificar() {
    val balancos = calcularBalancos()
    val valido = balancos.all { it >= valueMinimo }
    println(if (valido) "Valido" else "Invalido")
}

fun comportavel(data: Int, despesa: Int) {
    val balancos = calcularBalancos()
    for (dataCandidata in data..999) {
        var possivel = true
        for (dataFutura in dataCandidata..999) {
            if (balancos[dataFutura] - despesa < valueMinimo) {
                possivel = false
                break
            }
        }
        if (possivel) {
            println(dataCandidata)
            return
        }
    }
    println("Incomportavel")
}
