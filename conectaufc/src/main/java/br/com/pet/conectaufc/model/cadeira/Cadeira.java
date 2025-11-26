package br.com.pet.conectaufc.model.cadeira;

import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.model.material.Material;
import br.com.pet.conectaufc.model.professor.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tab_cadeira")
@AllArgsConstructor
public class Cadeira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String codigo;

    @ManyToMany(mappedBy = "cadeiras")
    private List<Professor> professores = new ArrayList<>();

    @OneToMany(mappedBy = "cadeira", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materiais = new ArrayList<>();

    public Cadeira(){

    }

    public Cadeira(CadeiraRequestDTO dto) {
        this.nome = dto.nome();
    }

    public Cadeira(Long idCadeira, String nome) {
        this.id = idCadeira;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void addMaterial(Material material){
        materiais.add(material);
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

    public String getCodigo() {
        return codigo;
    }

    public void addProfessorNaCadeira(Professor professor){
        professores.add(professor);
    }

    public void removeProfessorDaCadeira(Professor professor) {
        this.professores.remove(professor);
    }
}

