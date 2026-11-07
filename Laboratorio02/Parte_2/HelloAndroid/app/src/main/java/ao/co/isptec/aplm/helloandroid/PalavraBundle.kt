package ao.co.isptec.aplm.helloandroid

data class PalavraBundle(val palavras: List<String>) {

    val contar: Int
        get() = palavras.size

    val comprimentoMedio: Double
        get() = if (palavras.isEmpty()) 0.0 else palavras.map { it.length }.average()

    val palavraMaisLonga: String
        get() = palavras.maxByOrNull { it.length } ?: "Não disponível."
}