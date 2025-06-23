package br.com.voca.testes;

import com.google.gson.Gson;
import br.com.voca.service.EnderecoRequisicao;

import java.io.IOException;

public class Testes {
    public static void main(String[] args) throws IOException, InterruptedException {
        String cep = "40490114";
        Gson gson = new Gson();

        EnderecoRequisicao buscador = new EnderecoRequisicao();
        var endereco = buscador.buscar(cep);
        System.out.println(endereco.getLogradouro());

    }
}
