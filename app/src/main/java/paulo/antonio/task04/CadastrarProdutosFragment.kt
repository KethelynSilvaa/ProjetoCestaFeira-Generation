package paulo.antonio.task04

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import paulo.antonio.task04.databinding.FragmentCadastrarProdutosBinding
import paulo.antonio.task04.model.Categoria
import paulo.antonio.task04.model.Produtos

class CadastrarProdutosFragment : Fragment() {
    private lateinit var binding: FragmentCadastrarProdutosBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private var categoriaSelecionada = 0L
    private var produtoSelecionado: Produtos? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Está inflando o layout para ser visualizado
        binding = FragmentCadastrarProdutosBinding.inflate(layoutInflater, container, false)

        carregarDados()

        mainViewModel.listCategoria()

        mainViewModel.myCategoriaResponse.observe(viewLifecycleOwner){
                response -> Log.d("Requisicao", response.body().toString())
            spinnerCategoria(response.body())
        }

        /*binding.btnVoltar.setOnClickListener {
            inserirDados()
        }*/

        binding.btnAdicionarProduto.setOnClickListener {
            inserirDados()
        }

        return binding.root
    }

    private fun inserirDados() {
        val imgProd = "agsduygasd"
        val nomeProd = binding.inputProduto.text.toString()
        val pesoProd = binding.inputPeso.text.toString()
        val valorProd = binding.inputValor.text.toString()
        val descProd = binding.inputDescricao.text.toString()
        val categoria = Categoria(categoriaSelecionada, null, null)

        if (validarCampos(nomeProd, pesoProd, valorProd, descProd)) {
            val salvar: String

            if(produtoSelecionado != null){
                salvar ="Produto Atualizado"
                val produtos = Produtos(produtoSelecionado?.id!!, nomeProd, descProd, imgProd, pesoProd, valorProd, categoria)
                mainViewModel.addProduto(produtos)
            }else{
                salvar = "Produto Criado"
                val produtos = Produtos(0, nomeProd, descProd, imgProd, pesoProd, valorProd, categoria)
                mainViewModel.addProduto(produtos)}

            Toast.makeText(context, salvar, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_cadastrarProdutosFragment_to_listagemProdutosFragment)

        } else {
            Toast.makeText(context, "Verifique os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun carregarDados(){
        produtoSelecionado = mainViewModel.produtoSelecionado
        if (produtoSelecionado != null){
            binding.inputProduto.setText(produtoSelecionado?.nomeMarca)
            binding.inputValor.setText(produtoSelecionado?.valor)
            binding.inputPeso.setText(produtoSelecionado?.quantidade)
            binding.inputDescricao.setText(produtoSelecionado?.descricao)
           // binding.inputImg.setImageResource.(imagem)
        }


    }

    private fun validarCampos(
        nome: String,
        peso: String,
        valor: String,
        desc: String
    ): Boolean {

        return !((nome == "" || nome.length < 2 || nome.length > 30)
                || (peso == "" || peso.length < 2 || peso.length > 6)
                || (valor == "" || valor.length < 3 || valor.length > 5)
                || (desc == " " || desc.length < 2 || desc.length > 300))
    }

    private fun spinnerCategoria(listCategoria: List<Categoria>?) {
        if (listCategoria != null) {
            binding.inputCategoria.adapter =
                ArrayAdapter(
                    requireContext(),
                    androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                    listCategoria
                )

            binding.inputCategoria.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selected = binding.inputCategoria.selectedItem as Categoria
                        categoriaSelecionada = selected.id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
        }
    }
}