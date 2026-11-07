package ao.co.isptec.aplm.helloandroid
const val LIMITE_PALAVRA_LONGA = 4

fun gerarResumo(bundle: PalavraBundle): String {
    val ehLonga: (String) -> Boolean = { it.length > LIMITE_PALAVRA_LONGA }
    val palavrasLongas = bundle.palavras.filter(ehLonga)
    val avaliacao = when (bundle.contar) {
        0 -> "Nenhuma palavra encontrada!"
        1 -> "Apenas uma palavra!"
        else -> "Foram digitadas ${bundle.contar} palavras."
    }
    return "$avaliacao\n" +
            "Comprimento médio: ${"%.2f".format(bundle.comprimentoMedio)}\n" +
            "Palavra mais longa: ${bundle.palavraMaisLonga}\n" +
            "Palavras longas (> $LIMITE_PALAVRA_LONGA letras): " +
            palavrasLongas.joinToString().ifEmpty { "nenhuma" }
}