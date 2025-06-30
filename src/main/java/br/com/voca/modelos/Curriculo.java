package br.com.voca.modelos;
import jakarta.persistence.*;
import java.util.List;

//anotação da JPA para mapear uma classe como tabela
@Entity
//anotação da JPA para definir um nome para a tabela
@Table(name = "curriculo")
public class Curriculo {
    //anotação da JPA para definir um id para cada objeto Curriculo
    @Id
    //anotação da JPA para definir como o id vai ser gerado, nesse caso, será incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExperienciaProfissional>  experienciaProfissional;
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FormacaoAcademica> formacaoAcademica;
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Habilidades> habilidades;
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Idioma> idioma;
}
