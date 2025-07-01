package br.com.voca.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import br.com.voca.modelos.Endereco;
import com.google.gson.Gson;

public class EnderecoRequisicao {
    private String url = "https://viacep.com.br/ws/%s/json/"; // URL da API ViaCEP

    // Busca um endereço a partir de um CEP.
    public Endereco buscar(String cep) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(url,cep)))
                .build();

        try{
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Se a requisição for bem-sucedida, converte o JSON para um objeto Endereco.
            if (response.statusCode() == 200 && !response.body().contains("\"erro\" : true")) {
                Gson gson = new Gson();
                return gson.fromJson(response.body(), Endereco.class);
            } else {
                throw new IOException("CEP não encontrado ou resposta inválida");
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("CEP inválido: " + cep + e);
        }
    }
}