package br.com.pet.conectaufc.model.material;

import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.professor.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_material")
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
}


