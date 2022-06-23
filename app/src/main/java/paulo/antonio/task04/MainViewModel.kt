package paulo.antonio.task04

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import paulo.antonio.task04.api.Repository
import paulo.antonio.task04.model.Categoria
import paulo.antonio.task04.model.Produtos
import retrofit2.Response
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var produtoSelecionado : Produtos? = null

    private var _myCategoriaResponse = MutableLiveData<Response<List<Categoria>>>()

    val myCategoriaResponse: LiveData<Response<List<Categoria>>> =
        _myCategoriaResponse

    private var _myProdutoResponse = MutableLiveData<Response<List<Produtos>>>()

    val myProdutosResponse: LiveData<Response<List<Produtos>>> =
        _myProdutoResponse


    init {
        //listCategoria()
    }

    fun listCategoria(){
        viewModelScope.launch {
            try {
                val response = repository.listCategoria()
                _myCategoriaResponse.value = response
            } catch (e: Exception){
                Log.d("Erro Requisição", e.message.toString())
            }
        }
    }

    fun addProduto(produtos: Produtos){
        viewModelScope.launch {
            try{
                repository.addProduto(produtos)
            }catch (e: Exception){
                Log.d("Erro Adicionar Produtos", e.message.toString())
            }
        }
    }

    fun listProduto(){
        viewModelScope.launch {
            try {
                val response = repository.listProduto()
                _myProdutoResponse.value = response
            }catch (e: Exception){
                Log.d("Erro Listar Produtos", e.message.toString())
            }
        }
    }
    fun updateTarefa(produtos: Produtos){
        viewModelScope.launch {
        try {

            repository.updateProtuto(produtos)
            listProduto()

        }catch (e:Exception){
            Log.d("Error", e.message.toString())


        }
        }
    }
}