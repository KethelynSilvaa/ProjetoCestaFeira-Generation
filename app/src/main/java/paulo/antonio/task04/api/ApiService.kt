package paulo.antonio.task04.api

import paulo.antonio.task04.model.Categoria
import paulo.antonio.task04.model.Produtos
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @GET("categoria")
    suspend fun listCategoria(): Response<List<Categoria>>

    @POST("produtos")
    suspend fun addProduto(
        @Body produtos: Produtos
    ): Response<Produtos>

    @GET("produtos")
    suspend fun listProdutos() : Response<List<Produtos>>

    @PUT("atualizar Produto")
    suspend fun updadeProduto(
        @Body produtos: Produtos
    ): Response<Produtos>

}