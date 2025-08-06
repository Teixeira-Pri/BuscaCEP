package com.priscilateixeira.buscacep

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.priscilateixeira.buscacep.api.EnderecoAPI
import com.priscilateixeira.buscacep.api.RetrofitHelper
import com.priscilateixeira.buscacep.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                recuperarEndereco()
            }
        }

    }

    private suspend fun recuperarEndereco() {
        val cep = binding.editCep.text.toString()

        withContext(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            binding.textResultado.visibility = View.GONE
        }

        if (cep.isNotEmpty()) {
            try {
                val enderecoAPI = retrofit.create(EnderecoAPI::class.java)
                val retorno = enderecoAPI.recuperarEndereco(cep)

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                }

                if (retorno.isSuccessful) {
                    val endereco = retorno.body()
                    val rua = endereco?.logradouro ?: "Rua não encontrada"
                    val cidade = endereco?.localidade ?: "Cidade não encontrada"

                    withContext(Dispatchers.Main) {
                        binding.textResultado.text = "Endereço: $rua - $cidade"
                        binding.textResultado.visibility = View.VISIBLE
                    }

                    Log.i("info_endereco", "Endereço: $rua - $cidade")
                } else {
                    withContext(Dispatchers.Main) {
                        binding.textResultado.text = "Erro: resposta inválida"
                        binding.textResultado.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.textResultado.text = "Erro ao recuperar endereço"
                    binding.textResultado.visibility = View.VISIBLE
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
                binding.textResultado.text = "Digite um CEP válido"
                binding.textResultado.visibility = View.VISIBLE
            }
        }
    }



    /*private suspend fun recuperarEndereco() {
        var retorno : Response<Endereco>? = null
        try {
            val enderecoAPI = retrofit.create( EnderecoAPI::class.java)
            retorno = enderecoAPI.recuperarEndereco()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_endereco", "Erro ao recuperar ")
        }
        if ( retorno != null) {
            if ( retorno.isSuccessful ){
                val endereco = retorno.body()
                val rua = endereco?.logradouro
                val cidade = endereco?.localidade
                Log.i("info_endereco", "Endereço: $rua - $cidade")
            }
        }
    }*/
}