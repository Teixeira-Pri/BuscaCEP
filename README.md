# Busca de Endereço via CEP - Android Kotlin

Aplicativo Android simples desenvolvido em Kotlin para buscar e exibir o endereço completo a partir de um CEP informado pelo usuário, consumindo a API pública do [ViaCEP](https://viacep.com.br/).

---

## Funcionalidades

- Entrada do CEP pelo usuário.
- Requisição assíncrona utilizando Retrofit e Coroutines.
- Exibição do endereço completo (logradouro e localidade).
- Feedback visual com ProgressBar durante a busca.
- Tratamento básico de erros e validação de entrada.
- Layout responsivo com ConstraintLayout e ViewBinding.

---

## Tecnologias e Bibliotecas Utilizadas

- Kotlin
- Retrofit 2.9.0
- Gson Converter
- Coroutines (Dispatchers.IO e Main)
- ViewBinding
- ConstraintLayout
- API pública ViaCEP
- Permissão de internet configurada no AndroidManifest.xml

