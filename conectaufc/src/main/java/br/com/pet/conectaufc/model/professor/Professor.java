package br.com.pet.conectaufc.model.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tab_professor")
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(
            name = "professor_cadeira",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "cadeira_id")
    )
    private List<Cadeira> cadeiras = new ArrayList<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materiais = new ArrayList<>();

    public Professor() {

    }

    public Professor(ProfessorRequestDTO dto) {
        this.nome = dto.nome();
    }

    public List<Cadeira> getCadeiras() {
        return cadeiras;
    }

    public Long getId() {
        return id;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public String getNome() {
        return nome;
    }

    public void addMaterialDeProfessor(Material material) {
        this.materiais.add(material);
    }

    public boolean hasThisMaterial(Material material) {
        if (materiais.contains(material)) return true;
        return false;
    }

    public void removeMaterial(Material material){
        materiais.remove(material);
    }

}
