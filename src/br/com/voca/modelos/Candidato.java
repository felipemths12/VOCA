package br.com.voca.modelos;

import java.time.LocalDate; //biblioteca utilizada para representar datas
import java.time.format.DateTimeFormatter; //biblioteca para formatar e analisar datas
import java.time.format.DateTimeParseException; //classe para tratar erros de conversão

public class Candidato {
    private String nome; //atributo para armazenar o nome
    private LocalDate dataNascimento; //atributo para armazenar a data de nascimento
    private String email; //atributo para armazenar o e-mail
    private String telefone; //atributo usado para armazenar o número de telefone
    private String nacionalidade; //atributo para armazenar a nacionalidade

    //construtor vazio para o JavaFX
    public Candidato() {
    }

    //construtor completo para instanciar um objeto do tipo Candidato
    public Candidato (String nome, String dataNascimento, String email,
                      String telefone, String nacionalidade) {
        setNome(nome);
        setDataNascimento(dataNascimento);
        setEmail(email);
        setTelefone(telefone);
        setNacionalidade(nacionalidade);
    }

    //setter do nome com validação contra string nula ou vazia
    public void setNome (String nome) {
        if(nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    //setter do email com validação usando regex (formato nome@dominio.com ou nome@dominio.com.br e suas variações)
    public void setEmail(String email) {
        if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("E-mail inválido");
        }
    }

    //setter do telefone usando regex para validação (formato (xx) 9xxxx-xxxx)
    public void setTelefone(String telefone) {
        if (telefone.matches("^\\(\\d{2}\\) 9\\d{4}-\\d{4}$")) {
            this.telefone = telefone;
        } else {
            throw new IllegalArgumentException("Telefone inválido");
        }
    }

    /*setter da data de nascimento que utiliza um formatador para especificar o formato e converte a String em um
    atributo do tipo LocalDate seguindo o padrão dd/mm/aaaa*/
    public void setDataNascimento (String dataNascimento) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            this.dataNascimento = LocalDate.parse(dataNascimento, formatador);
        } catch (DateTimeParseException e) {
            //implementar o tratamento da exceção baseado no front-end
        }
    }

    //setter da nacionalidade om validação contra string nula ou vazia
    public void setNacionalidade(String nacionalidade) {
        if(nacionalidade != null && !nacionalidade.isBlank()) {
            this.nacionalidade = nacionalidade;
        }
    }
}
