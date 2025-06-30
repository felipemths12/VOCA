package br.com.voca.modelos;

import jakarta.persistence.*;

//anotação da JPA para mapear uma classe como tabela
@Entity
//anotação da JPA para definir um nome para a tabela
@Table(name = "endereco")
public class Endereco {
    //anotação da JPA para definir um id para cada objeto Endereco
    @Id
    //anotação da JPA para definir como o id vai ser gerado, nesse caso, será incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;

    // LINHA CORRIGIDA ABAIXO
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