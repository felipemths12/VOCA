package br.com.voca.modelos;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "curriculo")
public class Curriculo {
    @Id
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
