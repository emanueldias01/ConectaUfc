package br.com.pet.conectaufc.model.material;

import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.professor.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tab_material")
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "cadeira_id", nullable = false)
    private Cadeira cadeira;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    private String link;

    public Material(){

    }

    public Material(MaterialRequestDTO dto, Professor professor, Cadeira cadeira) {
        this.nome = dto.nome();
        this.cadeira = cadeira;
        this.professor = professor;
        this.link = dto.link();
    }

    public Cadeira getCadeira() {
        return cadeira;
    }

    public Long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getNome() {
        return nome;
    }
}


