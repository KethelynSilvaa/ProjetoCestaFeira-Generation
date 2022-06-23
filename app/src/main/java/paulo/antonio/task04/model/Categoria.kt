package paulo.antonio.task04.model

data class Categoria (
    val id: Long,
    val descricao: String?,
    val produto: List<Produtos>?,
) {

    override fun toString(): String {
        return descricao!!
    }
}