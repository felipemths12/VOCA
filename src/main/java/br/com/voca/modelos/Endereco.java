package br.com.voca.modelos;

import jakarta.persistence.*;

// Mapeia a classe para a tabela "endereco".
@Entity
@Table(name = "endereco")
public class Endereco {
    // Define o ID e a estratégia de geração.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;

    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL)
    private Curriculo curriculo;

    public Endereco (String cep, String logradouro, String bairro,
                     String localidade, String uf, String numero) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.numero = numero;
    }

    public Endereco() {

    }

    // Getters
    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public String getNumero() {
        return numero;
    }
}