package br.com.pet.conectaufc.model.cadeira;

import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.model.material.Material;
import br.com.pet.conectaufc.model.professor.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tab_cadeira")
@AllArgsConstructor
@NoArgsConstructor
public class Cadeira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "cadeiras")
    private List<Professor> professores = new ArrayList<>();

    @OneToMany(mappedBy = "cadeira", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materiais = new ArrayList<>();

    public Cadeira(CadeiraRequestDTO dto) {
        this.nome = dto.nome();
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

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

