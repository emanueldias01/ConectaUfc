package br.com.pet.conectaufc.model.material;

import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.professor.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_material")
@AllArgsConstructor
@NoArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cadeira_id", nullable = false)
    private Cadeira cadeira;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    private String link;

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
}


