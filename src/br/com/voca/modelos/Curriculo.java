package br.com.voca.modelos;

public class Curriculo {
    private String nome; //atributo para armazenar o nome
    private String areaFormacao; //atributo para armazenar a área de formação
    private String email; //atributo para armazenar o e-mail
    private String telefone; //atributo usado para armazenar o número de telefone
    private int anosExperiencia; //atributo para armazenar os anos de experiência
    private boolean experiencia; //atributo para verificar se possui experiência

    //construtor vazio para o JavaFX
    public Curriculo () {
    }

    //construtor completo da classe utilizando os setters para uma construção segura e com validações
    public Curriculo(String nome, String areaFormacao, String email,
                     String telefone, boolean experiencia, int anosExperiencia) {
        setNome(nome);
        setAreaFormacao(areaFormacao);
        setEmail(email);
        setTelefone(telefone);
        setExperiencia(experiencia);
        setAnosExperiencia(anosExperiencia);
    }

    //setter do nome com validação contra string nula ou vazia
    public void setNome (String nome) {
        if(nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    //setter da área de formação do usuário
    public void setAreaFormacao(String areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    //setter do email com validação usando regex
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

    //setter do booleano para verificar se o usuário possui experiência

    public void setExperiencia(boolean experiencia) {
        this.experiencia = experiencia;
    }

    /*setter dos anos de experiência
    se houver experiência (indicada pelo atributo booleano experiência, o valor deve
    ser maior que 0, caso contrário, é zerado*/
    public void setAnosExperiencia(int anosExperiencia) {
        if (experiencia) {
            if (anosExperiencia <= 0) {
                throw new IllegalArgumentException("Anos de experiência deve ser maior que 0.");
            }
            this.anosExperiencia = anosExperiencia;
        } else {
            this.anosExperiencia = 0;
        }
    }

}