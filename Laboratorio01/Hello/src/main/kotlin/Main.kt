package org.emanueldossantos

data class Movimento(
    val montante: Int,
    val data: Int,
    val periodicidade: Int,
    val tipo: Char,
    val descricao: String)

val lista = mutableListOf<Movimento>()

fun main() {
    var cmd: String
    do
    {
        cmd = readln()
        execCommand(cmd.trim())
    } while(cmd != "exit")
}

fun execCommand(cmd: String){
    val args = cmd.split(" ")
    when (args.get(0))
    {
        "i" -> inserir(Movimento(
            args.get(1).toInt(),
            args.get(2).toInt(),
            args.get(3).toInt(),
            args.get(4)[0],
            args.get(5))
        )
        "l" -> listar(args.get(1).toInt())
        "r" -> remover(args.get(1).toInt(), args.get(2).toInt())
        else -> println("cmd: comando desconhecido!")
    }
}

fun inserir(movimento: Movimento)
{
    lista.add(movimento)
}

fun listar(data: Int)
{
    var i: Int = 0
    for (item in lista.filter { it.data == data })
    {
        i++
        println("$i - $item")
    }
}

fun remover(data: Int, indice: Int)
{
    val listaDeRemocao = lista.filter { it.data == data }
    if (indice - 1 in listaDeRemocao.indices)
        lista.remove(listaDeRemocao[indice - 1])
    else
        println("Erro: Indice invalido para a data: $data")
}