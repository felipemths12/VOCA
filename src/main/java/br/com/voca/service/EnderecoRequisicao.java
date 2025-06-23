package br.com.voca.service;

//classes utilizadas para utilizar a API ViaCEP
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import br.com.voca.modelos.Endereco;
import com.google.gson.Gson;

public class EnderecoRequisicao {
    private String url = "https://viacep.com.br/ws/%s/json/"; //link da API ViaCEP


    //método responsável por criar o cliente, construir a requisição e obter a resposta com base no CEP passado na URL
    public Endereco buscar(String cep) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient(); //criando o cliente
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(url,cep)))
                .build(); //construindo a requisição usando a formatação de string para incluir o CEP passado dinamicamente

        try{
            //tentando fazer a requisição e obter as respostas como string
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            //se a requisição apresentar sucesso e sem erros, ela pega a resposta e já "preenche" a classe Endereco
            //instanciando um objeto automaticamente
            if (response.statusCode() == 200 && !response.body().contains("\"erro\" : true")) {
                Gson gson = new Gson();
                return gson.fromJson(response.body(), Endereco.class);
            } else {
                //caso não, uma exceção é lançada para apresentar o erro
                throw new IOException("CEP não encontrado ou resposta inválida");
            }

        } catch (IllegalArgumentException e) {
            //exceção para informar caso o cep seja inválido
            throw new IllegalArgumentException("CEP inválido: " + cep + e);
        }
    }
}
