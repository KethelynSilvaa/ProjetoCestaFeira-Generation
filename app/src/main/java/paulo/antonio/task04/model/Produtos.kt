package paulo.antonio.task04.model

data class Produtos(
    //tem que ter o mesmo nome da api!!!!
    var id: Long,
    var nomeMarca: String,
    var descricao: String,
    var imagem: String,
    var quantidade: String,
    var valor: String,
    var categoria: Categoria){

}